require cornucopia-base.inc
require cornucopia-from-git.inc

inherit fso-plugin
SRCREV = "${FSO_CORNUCOPIA_SRCREV}"
PR = "${INC_PR}.0"
PV = "0.2.0+gitr${SRCPV}"
S = "${WORKDIR}/git/tools/${PN}"
