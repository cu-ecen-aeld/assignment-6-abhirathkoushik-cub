# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit update-rc.d

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-abhirathkoushik-cub.git;protocol=ssh;branch=main"

PV = "1.0+git${SRCPV}"
SRCREV = "ec233c90c8c3d2c24c66a0cf61c21eeb3b79edc8"

# This sets your staging directory based on WORKDIR, where WORKDIR is defined at 
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
# We reference the "server" directory here to build from the "server" directory
# in your assignments repo
S = "${WORKDIR}/git/server"

FILES:${PN} += "${bindir}/aesdsocket"
FILES:${PN} += "${bindir}/aesdsocket-start-stop.sh"

TARGET_LDFLAGS += "-pthread -lrt"
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN}="aesdsocket-start-stop.sh"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
	install -d ${D}${bindir}
	install -m 0755 ${S}/aesdsocket ${D}${bindir}/
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/aesdsocket-start-stop.sh ${D}${sysconfdir}/init.d/
}
