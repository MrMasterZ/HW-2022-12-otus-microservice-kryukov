#!/bin/bash

IP=$1
DIR_SCRIPT=$( cd -P "$( dirname "$0" )" >/dev/null 2>&1 && pwd )
ARGS=`echo $*`

help ()
{
  echo "                     help"
  echo "Этот скрипт деплоит 03-microservice-k8s-crud-users"
  echo "Варианты запуска:"
  echo "$0 --help | -h   -- вывести help"
  echo "$0 <ip-v4>  -- где ip-v4 сетевой адрес интерфейса, который будет слушать БД приложения (minikube в той же подсети)"
  echo "$0 <ip-v4>  --images=<value>   -- ключ --image указывает на то, что будет пересобран image приложения (из директории app) и сохранен в images/app.tar"
  echo "(Возможные <value>: "
  echo "1) liq -- собрать image в liquibase/liquibase.tar"
  echo "2) app -- собрать image в images/app.tar"
  echo "3) all -- собрать и то и другое"
  exit
}

if [ $# -lt 1 ] || [ $# -gt 2 ]
then
  echo "Incorrect number of arguments passed"
  echo
  help
fi

if [ "$1" == "--help" ] || [ "$1" == "-h" ]
then
  help
fi

if [ $# -eq 2 ] && [ "$2" != "--images=liq" ] && [ "$2" != "--images=app" ] && [ "$2" != "--images=all" ]
then
  echo "2nd agrument is incorrect"
  echo
  help
fi

is_IP() {
if [ `echo $IP | grep -o '\.' | wc -l` -ne 3 ]; then
        echo "Parameter '$1' does not look like an IP Address (does not contain 3 dots).";
	echo
	help
        exit 1;
elif [ `echo $1 | tr '.' ' ' | wc -w` -ne 4 ]; then
        echo "Parameter '$1' does not look like an IP Address (does not contain 4 octets).";
	echo
	help
        exit 1;
else
        for OCTET in `echo $1 | tr '.' ' '`; do
                if ! [[ $OCTET =~ ^[0-9]+$ ]]; then
                        echo "Parameter '$1' does not look like in IP Address (octet '$OCTET' is not numeric).";
			echo
			help
                        exit 1;
                elif [[ $OCTET -lt 0 || $OCTET -gt 255 ]]; then
                        echo "Parameter '$1' does not look like in IP Address (octet '$OCTET' in not in range 0-255).";
			echo
			help
                        exit 1;
                fi
        done
fi

return 0;
}


is_IP $IP

######################################## USEFUL PART OF SCRIPT #############################################################################
minikube delete
minikube start
cd $DIR_SCRIPT
mkdir images
if [ "$2" == "--images=liq" ] || [ "$2" == "--images=all" ]
then
  docker rmi my-liquibase:v1
  docker build -t my-liquibase:v1 ./liquibase
  rm -f images/liquibase.tar
  docker save my-liquibase:v1 -o images/liquibase.tar
fi
minikube image load images/liquibase.tar
if [ "$2" == "--images=app" ] || [ "$2" == "--images=all" ]
then
  docker rmi 03-microservice-k8s-crud-users:v1
  docker build -t 03-microservice-k8s-crud-users:v1 ./app
  rm -f images/app.tar
  docker save 03-microservice-k8s-crud-users:v1 -o images/app.tar
fi
minikube image load images/app.tar
kubectl create namespace m  && kubectl config set-context --current --namespace=m
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx/
helm repo update
helm install nginx ingress-nginx/ingress-nginx --namespace m -f resource/nginx-ingress.yaml
helm install postgresql bitnami/postgresql
while [ `kubectl get pods -A | grep postgresql | grep Running 1>/dev/null; 2>&1 ; echo $?` -ne 0 ] || [ `kubectl get pods -A | grep postgresql  | awk ' { print $3 } '` != 1/1 ]
do
  sleep 1
done
kubectl port-forward --address $IP svc/postgresql 5432:5432 &
POSTGRES_PASSWORD=$(kubectl get secret --namespace m postgresql -o jsonpath="{.data.postgres-password}" | base64 -d)
sed -i -e "s/pass:.*/pass: $POSTGRES_PASSWORD/g" $DIR_SCRIPT/chart/values.yaml
sed -i -e "s/ip:.*/ip: $IP/g" $DIR_SCRIPT/chart/values.yaml
helm install crud-users $DIR_SCRIPT/chart

