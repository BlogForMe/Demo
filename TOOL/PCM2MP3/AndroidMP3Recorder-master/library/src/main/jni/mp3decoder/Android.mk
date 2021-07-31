LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

MAD_DIR := mad

LOCAL_SRC_FILES:= $(MAD_DIR)/version.c $(MAD_DIR)/fixed.c $(MAD_DIR)/bit.c $(MAD_DIR)/timer.c $(MAD_DIR)//stream.c $(MAD_DIR)/frame.c  $(MAD_DIR)/synth.c $(MAD_DIR)/decoder.c $(MAD_DIR)/layer12.c $(MAD_DIR)/layer3.c $(MAD_DIR)/huffman.c ./FileSystem.c ./NativeMP3Decoder.c 

LOCAL_ARM_MODE := arm

LOCAL_MODULE:= libmad

LOCAL_C_INCLUDES := \
    $(LOCAL_PATH)/android 

LOCAL_CFLAGS := -DHAVE_CONFIG_H -DFPM_ARM -ffast-math -O3

LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)
