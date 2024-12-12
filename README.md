# How to Add a New Device to the Repo

To add a new device to the repo, follow these steps:

### 1. Create the Device Configuration File
In `meta-smartphone/meta-${OEM_NAME}/conf/machine/`, create a configuration file for your device. The file should follow the format of the existing device configuration files, with necessary edits for your device. 

### 2. Create the Kernel Recipe
In `meta-smartphone/meta-${OEM_NAME}/recipes-kernel/linux/`, create a `.bb` file with a name similar to `linux-${OEM_NAME}-${DEVICE_CODENAME}.bb`. Modify the file as needed for your device, specifically adjusting the kernel source location. The easiest place to find kernel sources is typically LineageOS, but device OEM sources also work.

### 3. Create the Kernel Defconfig
In `meta-smartphone/meta-${OEM_NAME}/recipes-kernel/linux/linux-${OEM}-${DEVICE_CODENAME}/`, create a `defconfig` file. This can be based on the kernel code for your device, which is typically found in a location like `/arch/arm/configs/`.

### 4. Create the Firmware Recipe
In `meta-smartphone/meta-${OEM_NAME}/recipes-kernel/firmware/`, create a `.bb` file similar to `firmware-${OEM_NAME}-${DEVICE_CODENAME}.bb`. This file will define any custom proprietary firmware files needed for your device (e.g., modem, WiFi, NFC, camera, etc.). It’s best to source these from stock firmware or a fully working custom ROM.

### 5. Add Firmware Files
In `meta-smartphone/meta-${OEM_NAME}/recipes-kernel/firmware/firmware-${OEM_NAME}-${DEVICE_CODENAME}/`, place any locally sourced firmware files. These should not come from a git repository but should be direct files required for your device.

Ensure that each file is modified specifically for your device and its sources. Once completed, the device should be ready to build and integrate into the project.

# Setting Up the Build Environment
Before you can build, you will need some tools. If you try to build without them, bitbake will fail a sanity check and tell you about what's missing, but not really how to get the missing pieces. The essential and graphical support packages you need for a supported Ubuntu or Debian distribution are shown in the following commands: 
```
$ curl -sL https://deb.nodesource.com/setup_4.x | sudo bash -
$ sudo apt-get install gawk wget git-core diffstat unzip texinfo \
build-essential chrpath libsdl1.2-dev xterm nodejs bzr
```
For 64bit systems, install this as well: `sudo apt install gcc-multilib g++-multilib`

>**Note:**
>The steps below will setup your build environment for a development build. If you want to build the latest release use ENV_NAME=stable instead but keep in mind that all new developed features need to go into testing first before they are submitted to stable.
```
$ cd into-your-build-directory
$ mkdir webos-ports-env && cd webos-ports-env
$ export ENV_NAME=testing
$ wget https://raw.github.com/webOS-ports/webos-ports-setup/$ENV_NAME/Makefile
$ make setup-webos-ports
```
You need a lot of RAM to link Chromium, make sure you have at least 16GB (it's OK when some of that is swap, because it's used only for short part of build). Chromium needs so much ram to link because it's linking with debug symbols (huge files) which are stripped later in do_package after creating -dbg packages. 

# Building
Use the below commands to build LuneOS for the device. Steps are taken from WebOS-Ports Wiki.

Make sure you're _not_ root, as the build will fail. If you SU in the middle, don't forget to set up env afresh (. setup-env)

### To configure to build (notice '.' which is actually bash 'source' command):

```
$ cd into-your-build-directory/webos-ports-env/webos-ports
$ . setup-env
```

### To update metadata

```
# go one level up to the webos-ports-env dir
$ cd ..
$ make update
# or if it shows warning about different bblayers.conf or layers.txt
$ make update-conffiles && make update
# you can also add UPDATE_CONFFILES_ENABLED = 1 to config.mk
# if you never want to have any uncommited changes in your checkouts RESET_ENABLED = 1 in config.mk
# go back to the webos-ports dir to continue with the build process
$ cd webos-ports
```

### To build the luneos-dev-image for a device ${DEVICE_CODENAME}:

 `$ MACHINE=${DEVICE_CODENAME} bb luneos-dev-package`

After the build completes, you will find your image in `<build env>/tmp-glibc/deploy/images/${DEVICE_CODENAME}/`

>**NOTE:**
>If you are getting PermissionError when running on Ubuntu 24.10, run this command `sudo apparmor_parser -R /etc/apparmor.d/unprivileged_userns` outside of your build env. This is a bug in Yocto.

# Installing
Assuming the build completed without error (it will be obvious when it finishes).
Once you have built the image, you'll likely want to install it and run it on your device.

Remember: The file you want to sideload on your device is `<build env>/tmp-glibc/deploy/images/${DEVICE_CODENAME}/luneos-dev-package-${DEVICE_CODENAME}.zip`

If you also need the kernel, it's in `<build env>/tmp-glibc/deploy/images/${DEVICE_CODENAME}/uImage`
