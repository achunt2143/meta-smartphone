DESCRIPTION = "The PhoneUI app starters"
HOMEPAGE = "http://shr-project.org/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"
SECTION = "x11/applications"
DEPENDS += "dbus-glib"
SRCREV = "7d72f9065bcdf6950b04c60ad9183aef20dc2242"
PV = "0.0.0+gitr${SRCPV}"
PR = "r1"

inherit pkgconfig autotools

SRC_URI = "git://git.shr-project.org/repo/phoneui-apps.git;protocol=http;branch=master"
S = "${WORKDIR}/git"

PACKAGES = "${PN}-dialer ${PN}-messages ${PN}-contacts ${PN}-quick-settings"

FILES_${PN}-dialer = "\
	/usr/bin/phoneui-dialer \
	/usr/share/applications/phoneui-dialer.desktop \
"
FILES_${PN}-messages = "\
	/usr/bin/phoneui-messages \
	/usr/share/applications/phoneui-messages.desktop \
"
FILES_${PN}-contacts = "\
	/usr/bin/phoneui-contacts \
	/usr/share/applications/phoneui-contacts.desktop \
"

FILES_${PN}-quick-settings = "\
	/usr/bin/phoneui-quick-settings \
"

