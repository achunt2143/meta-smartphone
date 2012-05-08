require recipes-kernel/linux/linux.inc

SECTION = "kernel"
DESCRIPTION = "Modified kernel for the Palm Pre based on the original source from Palm Inc."
KERNEL_IMAGETYPE = "uImage"
COMPATIBLE_MACHINE = "(palmpre|palmpre2)"
SRCREV = "442f7e619d20d6b607c82bc42436788ffb82d66f"
KV = "2.6.24"
PE = "1"
PV = "${KV}+gitr${SRCPV}"
# for bumping PR bump MACHINE_KERNEL_PR in the machine config

SRC_URI = " \
  git://git.freesmartphone.org/linux-2.6.git;protocol=git;branch=palmpre/master \
  file://defconfig \
"
S = "${WORKDIR}/git/"
