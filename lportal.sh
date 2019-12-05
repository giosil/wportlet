#!/bin/bash

# Custom env
. $HOME/bin/lportal_env

# Portal name
LPORTAL_NAME="Liferay $LOGNAME"

# Check CATALINA_HOME
if [ -z "$CATALINA_HOME" -o ! -x "$CATALINA_HOME/bin/catalina.sh" ]
then
  echo "ERROR: \$CATALINA_HOME not found"
  exit 1
fi

# Check CATALINA_BASE
if [ -z "$CATALINA_BASE" -o ! -d "$CATALINA_BASE/webapps/ROOT" ]
then
  echo "ERROR: \$CATALINA_BASE not found"
  exit 1
fi

export LIFERAY_TOMCAT=$CATALINA_BASE

fs_start() {
  local TAIL_LOG=$1
  local NOW=$(date "+%Y%m%d-%H%M%S")

  echo -e "\nStarting $LPORTAL_NAME..."

  # Log backup
  if [ -f $LIFERAY_TOMCAT/logs/catalina.out ]
  then
    mv $LIFERAY_TOMCAT/logs/catalina.out $LIFERAY_TOMCAT/logs/catalina_$NOW.out
  fi
  if [ -f $LIFERAY_TOMCAT/logs/gc.log ]
  then
    mv $LIFERAY_TOMCAT/logs/gc.log $LIFERAY_TOMCAT/logs/gc_$NOW.log
  fi

  # Remove folders work and temp
  if [ -d $LIFERAY_TOMCAT/work ]
  then
    rm -rf $LIFERAY_TOMCAT/work
    mkdir -p $LIFERAY_TOMCAT/work
  fi
  if [ -d $LIFERAY_TOMCAT/temp ]
  then
    rm -rf $LIFERAY_TOMCAT/temp
    mkdir -p $LIFERAY_TOMCAT/temp
  fi

  # Custom options
  if [ -x $HOME/bin/lportal_opts ]
  then
    . $HOME/bin/lportal_opts
  elif [ -f $HOME/bin/lportal_opts ]
  then
    echo "File $HOME/bin/setenv.java_opts is not executable"
    exit -1
  fi
  echo -e "\nJAVA_OPTS=$JAVA_OPTS\n"
  echo -e "\nCATALINA_OPTS=$CATALINA_OPTS\n"
  
  # Start tomcat
  cd $CATALINA_HOME/bin
  ./startup.sh

  if [ "$TAIL_LOG" != "n" -a "$TAIL_LOG" != "N" ]
  then
    fs_log
  fi
}

fs_stop() {
  echo -e "\nStopping $LPORTAL_NAME..."
  cd $CATALINA_HOME/bin
  ./shutdown.sh
}

fs_running() {
  ps -C java -f | grep -c $CATALINA_HOME/bin
}

fs_status() {
  if [ "$(fs_running)" != "1" ]
  then
    echo "$LPORTAL_NAME is not running"
  fi

  ps -C java -f | grep $CATALINA_HOME/bin
}

fs_get_pid() {
  ps -C java -f | grep $CATALINA_HOME/bin | while read a b c; do echo -e "$b \c"; done
}

fs_log() {
  tail -f $LIFERAY_TOMCAT/logs/catalina.out
}

fs_halt() {
  local kill_tries=$1
  local tries=0

  if [ -z "$kill_tries" ]
  then
    kill_tries=0
  fi

  if [ "$(fs_running)" -ge 1 ]
  then
    fs_stop
  fi

  while [ "$(fs_running)" -ge 1 ]
  do
    echo "Wait for halt $LPORTAL_NAME..."
    sleep 3

    if [ $kill_tries -gt 0 ]
    then
      tries=$((tries+1))
      if [ $tries -ge $kill_tries ]
      then
        P=$(fs_get_pid)
        echo "kill -9 $P"
        kill -9 $P
      fi
    fi
  done
}

fs_restart() {
  fs_halt 8
  fs_start
}

case $1 in
  start)
    fs_start
    ;;
  start_bg)
    fs_start "N"
    ;;
  stop)
    fs_stop
    ;;
  status)
    fs_status
    ;;
  log)
    fs_log
    ;;
  restart)
    fs_restart
    ;;
  halt)
    fs_halt
    ;;
  kill)
    fs_halt 5
    ;;
  *)
    echo "Unknow action. Use {start|start_bg|stop|status|log|restart|halt|kill}"
  ;;
esac
