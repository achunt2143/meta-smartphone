require cornucopia.inc
DESCRIPTION = "freesmartphone.org transport library"
SRCREV = "${FSO_CORNUCOPIA_SRCREV}"
PV = "0.10.0+gitr${SRCPV}"
PE = "1"
PR = "${INC_PR}.0"

EXTRA_OECONF = "--enable-palmpre-hsuart"
