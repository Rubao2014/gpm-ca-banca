# Atencao: para este command funcionar, ele deve ser configurado
# como executável no Terminal do Mac com o comando abaixo:
#
# chmod +x jarvis.command
# 
APP_PKS_DIR=$(dirname $0)
java -jar /Users/pekus/Documents/biblio/jarvis/android/jarvis.jar $APP_PKS_DIR
# osascript -e 'tell application "Terminal" to close (every window whose name contains ".command")' & exit