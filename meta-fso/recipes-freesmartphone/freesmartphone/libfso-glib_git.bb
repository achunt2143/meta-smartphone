DESCRIPTION = "GLib-based DBus bindings for freesmartphone.org - Vala implementation"
AUTHOR = "Didier 'Ptitjes"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=fad9b3332be894bab9bc501572864b29"
SECTION = "devel"
DEPENDS = "vala-dbus-binding-tool-native vala-dbus-binding-tool glib-2.0 fso-specs"
SRCREV = "05a7c4a13693006324d26d9bbcc799f595a851d9"
PV = "2011.09.22.1+gitr${SRCPV}"
PE = "1"

inherit autotools vala

SRC_URI = "${FREESMARTPHONE_GIT}/libfso-glib.git;protocol=git;branch=gdbus"
S = "${WORKDIR}/git"

do_configure_prepend() {
       sed -i -e s#FSO_SPECS_DIR="#FSO_SPECS_DIR="${STAGING_DIR_HOST}/#g ${S}/configure.ac
}
