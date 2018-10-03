#
# This class is used to create Android device compatible kernel images
#

ANDROID_BOOTIMG_CMDLINE ?= ""
ANDROID_BOOTIMG_KERNEL_RAM_BASE ?= "Please set to right value!"
ANDROID_BOOTIMG_RAMDISK_RAM_BASE ?= "0x00000000"
ANDROID_BOOTIMG_SECOND_RAM_BASE ?= "0x00000000"
ANDROID_BOOTIMG_TAGS_RAM_BASE ?= "0x00000000"
ANDROID_BOOTIMG_EXTRA_ABOOTIMG_ARGS ?= ""

KERNEL_OUTPUT ?= "${KERNEL_OUTPUT_DIR}/${KERNEL_IMAGETYPE}"

INITRAMFS_NAME = "initramfs-android-image-${MACHINE}${IMAGE_NAME_SUFFIX}.cpio.gz"

do_deploy[depends] += "initramfs-android-image:do_image_complete abootimg-native:do_populate_sysroot"
do_deploy_append() {
    if [ ! -e ${DEPLOY_DIR_IMAGE}/${INITRAMFS_NAME} ] ; then
        bbfatal "Required initramfs image ${DEPLOY_DIR_IMAGE}/${INITRAMFS_NAME} is not available!"
    fi

    abootimg --create ${B}/boot.img \
             -k ${B}/${KERNEL_OUTPUT} \
             -r ${DEPLOY_DIR_IMAGE}/${INITRAMFS_NAME} \
             -c "cmdline=${ANDROID_BOOTIMG_CMDLINE}" \
             -c "kerneladdr=${ANDROID_BOOTIMG_KERNEL_RAM_BASE}" \
             -c "ramdiskaddr=${ANDROID_BOOTIMG_RAMDISK_RAM_BASE}" \
             -c "secondaddr=${ANDROID_BOOTIMG_SECOND_RAM_BASE}" \
             -c "tagsaddr=${ANDROID_BOOTIMG_TAGS_RAM_BASE}" \
             ${ANDROID_BOOTIMG_EXTRA_ABOOTIMG_ARGS}

    # We're probably interested only in zImage KERNEL_IMAGETYPE, but keep
    # the for loop for consistency with other bbclasses
    for type in ${KERNEL_IMAGETYPES} ; do
        cp ${B}/boot.img ${DEPLOYDIR}/${type}-${KERNEL_IMAGE_NAME}.fastboot
        ln -snvf ${type}-${KERNEL_IMAGE_NAME}.fastboot ${DEPLOYDIR}/${type}-${KERNEL_IMAGE_LINK_NAME}.fastboot
        ln -snvf ${type}-${KERNEL_IMAGE_NAME}.fastboot ${DEPLOYDIR}/${type}.fastboot
    done
}
