#! /usr/bin/env bash

set -e

# This script transforms the DaveGut/TP-Link-SmartThings repo so that
# it will import via the Smartthings web IDE via Github
# See https://docs.smartthings.com/en/latest/tools-and-ide/github-integration.html?highlight=repo#repository-structure


# move smartapp to format of:
#   smartapps/<namespace>/<smartapp-name>.src/<smartapp-file>.groovy
fix-smartapp() {
  src_path="Service Manager/TPLink Cloud Connect V2.groovy"
  dst_file="tp-link-cloud-connect.groovy"
  dst_path="smartapps/davegut/tp-link-cloud-connect.src/${dst_file}"

  echo "##### SMARTAPP: ${src_path} #####"

  echo mkdir -p $(dirname "${dst_path}")
  mkdir -p $(dirname "${dst_path}")

  echo mv "${src_path}" "${dst_path}"
  mv "${src_path}" "${dst_path}"

  echo sed -i '' 's/(Cloud) //g' "${dst_path}"
  sed -i '' 's/(Cloud) //g' "${dst_path}"
}


# move each device to format of:
#  devicetypes/<namespace>/<device-type-name>.src/<device-handler-file>.groovy
fix-devices() {
  OIFS="$IFS"
  IFS=$'\n'

  #find Device\ Handlers -name "*.groovy" -exec fix-devices {} \;
  devices=$(ls Device\ Handlers/*)
  for d in ${devices}; do
    fix-device ${d}
  done

  IFS="$OIFS"
}

# given a device src file, move it into correct location
fix-device() {
  src_path="${@}"
  src_file=$(basename "${src_path}")
  dst_file=$(echo "${src_file}" | cut -d ' ' -f 2- | tr -d '()'| tr '[:upper:]' '[:lower:]'| tr ' ' '-')
  dst_path="devicetypes/davegut/${dst_file/.groovy/.src}/${dst_file}"

  echo
  echo "##### DEVICE: ${src_path} #####"

  echo mkdir -p $(dirname "${dst_path}")
  mkdir -p $(dirname "${dst_path}")

  echo mv "${src_path}" "${dst_path}"
  mv "${src_path}" "${dst_path}"

  echo sed -i '' 's/name: "(${installType}) /name: "/' "${dst_path}"
  sed -i '' 's/name: "(${installType}) /name: "/' "${dst_path}"
}

main() {
  fix-smartapp
  fix-devices
}

main
