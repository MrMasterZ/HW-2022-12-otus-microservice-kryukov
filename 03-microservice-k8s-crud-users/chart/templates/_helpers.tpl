{{/*
Create service name (with appVersion)
*/}}
{{- define "chart.service" -}}
{{- printf "%s-%s"  .Values.project.name .Chart.AppVersion | replace "." "-" | trunc 63 }}
{{- end }}
