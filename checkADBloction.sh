FILENAME_RESULT=$(adb shell ls / | tr -d '\015' 
| grep 'devices')

if [ -z "$FILENAME_RESULT" ];
then 
	echo "NO fileName found."
else
	echo "fileName found."
fi
