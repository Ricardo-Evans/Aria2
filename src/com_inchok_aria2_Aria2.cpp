//
// Created by inCHOK on 2018/9/23.
//
#include <jni.h>
#include <string>
#include <android/log.h>
#include "aria2.h"
#include "com_inchok_aria2_Aria2.h"

#ifdef __cplusplus
extern "C" {
#endif

int downloadEventCallback(aria2::Session *session, aria2::DownloadEvent event, aria2::A2Gid gid, void *) {
	if (downloadCallback != nullptr && javaVM != nullptr) {
		JNIEnv *env;
		javaVM->AttachCurrentThread(&env, nullptr);
		jclass sessionClass = env->FindClass("com/inchok/aria2/Session");
		jmethodID sessionConstructorMethodID = env->GetMethodID(sessionClass, "<init>", "(J)V");
		jobject javaSession = env->NewObject(sessionClass, sessionConstructorMethodID, (jlong) session);
		jclass downloadEventClass = env->FindClass("com/inchok/aria2/DownloadEvent");
		std::string name;
		switch (event) {
			case aria2::EVENT_ON_DOWNLOAD_START: {
				name = "ON_DOWNLOAD_START";
				break;
			}
			case aria2::EVENT_ON_DOWNLOAD_PAUSE: {
				name = "ON_DOWNLOAD_PAUSE";
				break;
			}
			case aria2::EVENT_ON_DOWNLOAD_STOP: {
				name = "ON_DOWNLOAD_STOP";
				break;
			}
			case aria2::EVENT_ON_DOWNLOAD_COMPLETE: {
				name = "ON_DOWNLOAD_COMPLETE";
				break;
			}
			case aria2::EVENT_ON_DOWNLOAD_ERROR: {
				name = "ON_DOWNLOAD_ERROR";
				break;
			}
			case aria2::EVENT_ON_BT_DOWNLOAD_COMPLETE: {
				name = "ON_BT_DOWNLOAD_COMPLETE";
				break;
			}
		}
		jfieldID downloadEventFieldID = env->GetStaticFieldID(downloadEventClass, name.c_str(), "Lcom/inchok/aria2/DownloadEvent;");
		jobject javaDownloadEvent = env->GetStaticObjectField(downloadEventClass, downloadEventFieldID);
		jclass gidClass = env->FindClass("com/inchok/aria2/Gid");
		jmethodID gidConstructorMethodID = env->GetMethodID(gidClass, "<init>", "(J)V");
		jobject javaGid = env->NewObject(gidClass, gidConstructorMethodID, (jlong) gid);
		jclass downloadCallbackClass = env->GetObjectClass(*downloadCallback);
		return env->CallIntMethod(*downloadCallback, env->GetMethodID(downloadCallbackClass, "onDownloadEvent", "(Lcom/inchok/aria2/Session;Lcom/inchok/aria2/DownloadEvent;Lcom/inchok/aria2/Gid;)I"), javaSession, javaDownloadEvent, javaGid);
	}
	return 0;
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *) {
	javaVM = vm;
	JNIEnv *env;
	if (javaVM->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
		return -1;
	}
	return JNI_VERSION_1_6;
}

JNIEXPORT void JNICALL JNI_OnUnload(JavaVM *vm, void *) {
	JNIEnv *env;
	if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK)
		return;
	if (downloadCallback != NULL) {
		env->DeleteGlobalRef(*downloadCallback);
		delete downloadCallback;
	}
	javaVM = nullptr;
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_initializeNative(JNIEnv *, jclass) {
	return (jint) aria2::libraryInit();
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_deInitializeNative(JNIEnv *, jclass) {
	return (jint) aria2::libraryDeinit();
}

JNIEXPORT jlong JNICALL Java_com_inchok_aria2_Aria2_newSessionNative(JNIEnv *env, jclass, jobject options, jobject sessionConfig) {
	aria2::SessionConfig config;
	config.downloadEventCallback = downloadEventCallback;
	jclass sessionConfigClass = env->GetObjectClass(sessionConfig);
	config.keepRunning = env->CallBooleanMethod(sessionConfig, env->GetMethodID(sessionConfigClass, "isKeepRunning", "()Z"));
	config.useSignalHandler = env->CallBooleanMethod(sessionConfig, env->GetMethodID(sessionConfigClass, "isUseSignalHandler", "()Z"));
	downloadCallback = new jobject;
	*downloadCallback = env->NewGlobalRef(env->CallObjectMethod(sessionConfig, env->GetMethodID(sessionConfigClass, "getDownloadCallback", "()Lcom/inchok/aria2/DownloadCallback;")));
	aria2::KeyVals optionsNative;
	jclass keyValuesClass = env->FindClass("com/inchok/aria2/KeyValues");
	jmethodID keyValuesSizeMethodID = env->GetMethodID(keyValuesClass, "size", "()I");
	jmethodID keyValuesGetMethodID = env->GetMethodID(keyValuesClass, "get", "(I)Ljava/lang/Object;");
	jclass pairClass = env->FindClass("com/inchok/aria2/Pair");
	jmethodID pairGetKeyMethodID = env->GetMethodID(pairClass, "getKey", "()Ljava/lang/String;");
	jmethodID pairGetValueMethodID = env->GetMethodID(pairClass, "getValue", "()Ljava/lang/String;");
	for (int i = 0; i < (int) env->CallIntMethod(options, keyValuesSizeMethodID); ++i) {
		jobject option = env->CallObjectMethod(options, keyValuesGetMethodID, i);
		jstring key = (jstring) env->CallObjectMethod(option, pairGetKeyMethodID);
		jstring value = (jstring) env->CallObjectMethod(option, pairGetValueMethodID);
		const char *keyNative = env->GetStringUTFChars(key, JNI_FALSE);
		const char *valueNative = env->GetStringUTFChars(value, JNI_FALSE);
		optionsNative.push_back(std::pair<std::string, std::string>(keyNative, valueNative));
		env->ReleaseStringUTFChars(key, keyNative);
		env->ReleaseStringUTFChars(value, valueNative);
		env->DeleteLocalRef(option);
		env->DeleteLocalRef(key);
		env->DeleteLocalRef(value);
	}
	session = aria2::sessionNew(optionsNative, config);
	return (jlong) session;
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_finalSessionNative(JNIEnv *, jclass, jlong session) {
	return (jint) aria2::sessionFinal((aria2::Session *) session);
}

JNIEXPORT jlong JNICALL Java_com_inchok_aria2_Aria2_hexToGidNative(JNIEnv *env, jclass, jstring gid) {
	return (jlong) aria2::hexToGid(env->GetStringUTFChars(gid, JNI_FALSE));
}

JNIEXPORT jstring JNICALL Java_com_inchok_aria2_Aria2_gidToHexNative(JNIEnv *env, jclass, jlong gid) {
	return env->NewStringUTF(aria2::gidToHex((aria2::A2Gid) gid).c_str());
}

JNIEXPORT jboolean JNICALL Java_com_inchok_aria2_Aria2_isNullNative(JNIEnv *, jclass, jlong gid) {
	return (jboolean) (aria2::isNull((aria2::A2Gid) gid) ? JNI_TRUE : JNI_FALSE);
}

JNIEXPORT jobject JNICALL Java_com_inchok_aria2_Aria2_getStatusNative(JNIEnv *env, jclass, jlong downloadHandleNative) {
	jclass downloadStatusClass = env->FindClass("com/inchok/aria2/DownloadStatus");
	std::string name;
	switch (((aria2::DownloadHandle *) downloadHandleNative)->getStatus()) {
		case aria2::DOWNLOAD_ACTIVE: {
			name = "ACTIVE";
			break;
		}
		case aria2::DOWNLOAD_WAITING: {
			name = "WAITING";
			break;
		}
		case aria2::DOWNLOAD_PAUSED: {
			name = "PAUSED";
			break;
		}
		case aria2::DOWNLOAD_COMPLETE: {
			name = "COMPLETE";
			break;
		}
		case aria2::DOWNLOAD_ERROR: {
			name = "ERROR";
			break;
		}
		case aria2::DOWNLOAD_REMOVED: {
			name = "REMOVED";
			break;
		}
	}
	jfieldID downloadStatusFieldID = env->GetStaticFieldID(downloadStatusClass, name.c_str(), "Lcom/inchok/aria2/DownloadStatus;");
	jobject downloadStatus = env->GetStaticObjectField(downloadStatusClass, downloadStatusFieldID);
	return downloadStatus;
}

JNIEXPORT jlong JNICALL Java_com_inchok_aria2_Aria2_getTotalLengthNative(JNIEnv *, jclass, jlong downloadHandleNative) {
	return (jlong) ((aria2::DownloadHandle *) downloadHandleNative)->getTotalLength();
}

JNIEXPORT jlong JNICALL Java_com_inchok_aria2_Aria2_getCompletedLengthNative(JNIEnv *, jclass, jlong downloadHandleNative) {
	return (jlong) ((aria2::DownloadHandle *) downloadHandleNative)->getCompletedLength();
}

JNIEXPORT jlong JNICALL Java_com_inchok_aria2_Aria2_getUploadedLengthNative(JNIEnv *, jclass, jlong downloadHandleNative) {
	return (jlong) ((aria2::DownloadHandle *) downloadHandleNative)->getUploadLength();
}

JNIEXPORT jstring JNICALL Java_com_inchok_aria2_Aria2_getBitFieldNative(JNIEnv *env, jclass, jlong downloadHandleNative) {
	return env->NewStringUTF(((aria2::DownloadHandle *) downloadHandleNative)->getBitfield().c_str());
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_getDownloadSpeedNative(JNIEnv *, jclass, jlong downloadHandleNative) {
	return (jint) ((aria2::DownloadHandle *) downloadHandleNative)->getDownloadSpeed();
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_getUploadSpeedNative(JNIEnv *, jclass, jlong downloadHandleNative) {
	return (jint) ((aria2::DownloadHandle *) downloadHandleNative)->getUploadSpeed();
}

JNIEXPORT jstring JNICALL Java_com_inchok_aria2_Aria2_getInfoHashNative(JNIEnv *env, jclass, jlong downloadHandleNative) {
	return env->NewStringUTF(((aria2::DownloadHandle *) downloadHandleNative)->getInfoHash().c_str());
}

JNIEXPORT jlong JNICALL Java_com_inchok_aria2_Aria2_getPieceLengthNative(JNIEnv *, jclass, jlong downloadHandleNative) {
	return (jlong) ((aria2::DownloadHandle *) downloadHandleNative)->getPieceLength();
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_getPieceCountNative(JNIEnv *, jclass, jlong downloadHandleNative) {
	return (jint) ((aria2::DownloadHandle *) downloadHandleNative)->getNumPieces();
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_getConnectionCountNative(JNIEnv *, jclass, jlong downloadHandleNative) {
	return (jint) ((aria2::DownloadHandle *) downloadHandleNative)->getConnections();
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_getErrorCodeNative(JNIEnv *, jclass, jlong downloadHandleNative) {
	return (jint) ((aria2::DownloadHandle *) downloadHandleNative)->getErrorCode();
}

JNIEXPORT jobject JNICALL Java_com_inchok_aria2_Aria2_getFollowedByNative(JNIEnv *env, jclass, jlong downloadHandleNative) {
	jclass listClass = env->FindClass("java/util/ArrayList");
	jmethodID listConstructorID = env->GetMethodID(listClass, "<init>", "()V");
	jobject follows = env->NewObject(listClass, listConstructorID);
	jmethodID listAddMethodID = env->GetMethodID(listClass, "add", "(Ljava/lang/Object;)Z");
	jclass longClass = env->FindClass("java/lang/Long");
	jmethodID longConstructorID = env->GetMethodID(longClass, "<init>", "(J)V");
	for (const aria2::A2Gid &gidNative:((aria2::DownloadHandle *) downloadHandleNative)->getFollowedBy()) {
		jobject gid = env->NewObject(longClass, longConstructorID, (jlong) gidNative);
		env->CallBooleanMethod(follows, listAddMethodID, gid);
		env->DeleteLocalRef(gid);
	}
	return follows;
}

JNIEXPORT jlong JNICALL Java_com_inchok_aria2_Aria2_getFollowingNative(JNIEnv *, jclass, jlong downloadHandleNative) {
	return (jlong) ((aria2::DownloadHandle *) downloadHandleNative)->getFollowing();
}

JNIEXPORT jlong JNICALL Java_com_inchok_aria2_Aria2_getBelongsToNative(JNIEnv *, jclass, jlong downloadHandleNative) {
	return (jlong) ((aria2::DownloadHandle *) downloadHandleNative)->getBelongsTo();
}

JNIEXPORT jstring JNICALL Java_com_inchok_aria2_Aria2_getDirNative(JNIEnv *env, jclass, jlong downloadHandleNative) {
	return env->NewStringUTF(((aria2::DownloadHandle *) downloadHandleNative)->getDir().c_str());
}

JNIEXPORT jobject JNICALL Java_com_inchok_aria2_Aria2_getFilesDataNative(JNIEnv *env, jclass, jlong downloadHandleNative) {
	jclass listClass = env->FindClass("java/util/ArrayList");
	jmethodID listConstructorID = env->GetMethodID(listClass, "<init>", "()V");
	jobject filesData = env->NewObject(listClass, listConstructorID);
	jmethodID listAddMethodID = env->GetMethodID(listClass, "add", "(Ljava/lang/Object;)Z");
	jclass fileDataClass = env->FindClass("com/inchok/aria2/FileData");
	jmethodID fileDataConstructorID = env->GetMethodID(fileDataClass, "<init>", "(ILjava/lang/String;JJZLjava/util/List;)V");
	jclass uriDataClass = env->FindClass("com/inchok/aria2/UriData");
	jmethodID uriDataConstructorID = env->GetMethodID(uriDataClass, "<init>", "(Ljava/lang/String;Lcom/inchok/aria2/UriStatus;)V");
	jclass uriStatusClass = env->FindClass("com/inchok/aria2/UriStatus");
	for (aria2::FileData &fileDataNative:((aria2::DownloadHandle *) downloadHandleNative)->getFiles()) {
		jobject urisData = env->NewObject(listClass, listConstructorID);
		for (aria2::UriData &uriDataNative:fileDataNative.uris) {
			std::string name;
			switch (uriDataNative.status) {
				case aria2::URI_USED: {
					name = "USED";
					break;
				}
				case aria2::URI_WAITING: {
					name = "WAITING";
					break;
				}
			}
			jfieldID uriStatusFieldID = env->GetStaticFieldID(uriStatusClass, name.c_str(), "Lcom/inchok/aria2/UriStatus");
			jobject uriStatus = env->GetStaticObjectField(uriStatusClass, uriStatusFieldID);
			jstring uriNative = env->NewStringUTF(uriDataNative.uri.c_str());
			jobject uriData = env->NewObject(uriDataClass, uriDataConstructorID, uriNative, uriStatus);
			env->CallBooleanMethod(urisData, listAddMethodID, uriData);
			env->DeleteLocalRef(uriStatus);
			env->DeleteLocalRef(uriNative);
			env->DeleteLocalRef(uriData);
		}
		jstring path = env->NewStringUTF(fileDataNative.path.c_str());
		jobject fileData = env->NewObject(fileDataClass, fileDataConstructorID, (jint) fileDataNative.index, path, (jlong) fileDataNative.length, (jlong) fileDataNative.completedLength, (jboolean) fileDataNative.selected ? JNI_TRUE : JNI_FALSE, urisData);
		env->CallBooleanMethod(filesData, listAddMethodID, fileData);
		env->DeleteLocalRef(path);
		env->DeleteLocalRef(fileData);
	}
	return filesData;
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_getFileCountNative(JNIEnv *, jclass, jlong downloadHandleNative) {
	return (jint) ((aria2::DownloadHandle *) downloadHandleNative)->getNumFiles();
}

JNIEXPORT jobject JNICALL Java_com_inchok_aria2_Aria2_getFileNative(JNIEnv *env, jclass, jlong downloadHandleNative, jint index) {
	aria2::FileData fileDataNative = ((aria2::DownloadHandle *) downloadHandleNative)->getFile((int) index);
	jclass listClass = env->FindClass("java/util/ArrayList");
	jmethodID listConstructorID = env->GetMethodID(listClass, "<init>", "()V");
	jmethodID listAddMethodID = env->GetMethodID(listClass, "add", "(Ljava/lang/Object;)Z");
	jclass fileDataClass = env->FindClass("com/inchok/aria2/FileData");
	jmethodID fileDataConstructorID = env->GetMethodID(fileDataClass, "<init>", "(ILjava/lang/String;JJZLjava/util/List;)V");
	jclass uriDataClass = env->FindClass("com/inchok/aria2/UriData");
	jmethodID uriDataConstructorID = env->GetMethodID(uriDataClass, "<init>", "(Ljava/lang/String;Lcom/inchok/aria2/UriStatus;)V");
	jclass uriStatusClass = env->FindClass("com/inchok/aria2/UriStatus");
	jobject urisData = env->NewObject(listClass, listConstructorID);
	for (aria2::UriData &uriDataNative:fileDataNative.uris) {
		std::string name;
		switch (uriDataNative.status) {
			case aria2::URI_USED: {
				name = "USED";
				break;
			}
			case aria2::URI_WAITING: {
				name = "WAITING";
				break;
			}
		}
		jfieldID uriStatusFieldID = env->GetStaticFieldID(uriStatusClass, name.c_str(), "Lcom/inchok/aria2/UriStatus");
		jobject uriStatus = env->GetStaticObjectField(uriStatusClass, uriStatusFieldID);
		jstring uri = env->NewStringUTF(uriDataNative.uri.c_str());
		jobject uriData = env->NewObject(uriDataClass, uriDataConstructorID, uri, uriStatus);
		env->CallBooleanMethod(urisData, listAddMethodID, uriData);
		env->DeleteLocalRef(uriStatus);
		env->DeleteLocalRef(uri);
		env->DeleteLocalRef(uriData);
	}
	return env->NewObject(fileDataClass, fileDataConstructorID, (jint) fileDataNative.index, env->NewStringUTF(fileDataNative.path.c_str()), (jlong) fileDataNative.length, (jlong) fileDataNative.completedLength, (jboolean) fileDataNative.selected ? JNI_TRUE : JNI_FALSE, urisData);
}

JNIEXPORT jobject JNICALL Java_com_inchok_aria2_Aria2_getBtMetaInfoNative(JNIEnv *env, jclass, jlong downloadHandleNative) {
	aria2::BtMetaInfoData btMetaInfoDataNative = ((aria2::DownloadHandle *) downloadHandleNative)->getBtMetaInfo();
	jclass listClass = env->FindClass("java/util/ArrayList");
	jmethodID listConstructorID = env->GetMethodID(listClass, "<init>", "()V");
	jmethodID listAddMethodID = env->GetMethodID(listClass, "add", "(Ljava/lang/Object;)Z");
	jobject announceList = env->NewObject(listClass, listConstructorID);
	for (std::vector<std::string> &announcesNative:btMetaInfoDataNative.announceList) {
		jobject announces = env->NewObject(listClass, listConstructorID);
		for (std::string &announceNative:announcesNative) {
			jstring announce = env->NewStringUTF(announceNative.c_str());
			env->CallBooleanMethod(announces, listAddMethodID, announce);
			env->DeleteLocalRef(announce);
		}
		env->CallBooleanMethod(announceList, listAddMethodID, announces);
		env->DeleteLocalRef(announces);
	}
	jclass btMetaInfoDataClass = env->FindClass("com/inchok/aria2/BtMetaInfoData");
	jmethodID btMetaInfoDataConstructorID = env->GetMethodID(btMetaInfoDataClass, "<init>", "(Ljava/util/List;Ljava/lang/String;Ljava/util/Date;Lcom/inchok/aria2/BtFileMode;Ljava/lang/String;)V");
	jclass dateClass = env->FindClass("java/util/Date");
	jmethodID dateConstructorID = env->GetMethodID(dateClass, "<init>", "(J)V");
	jclass btFileModeClass = env->FindClass("com/inchok/aria2/BtFileMode");
	std::string name;
	switch (btMetaInfoDataNative.mode) {
		case aria2::BT_FILE_MODE_NONE: {
			name = "NONE";
			break;
		}
		case aria2::BT_FILE_MODE_SINGLE: {
			name = "SINGLE";
			break;
		}
		case aria2::BT_FILE_MODE_MULTI: {
			name = "MULTI";
			break;
		}
	}
	jfieldID btFileModeFieldID = env->GetStaticFieldID(btFileModeClass, name.c_str(), "Lcom/inchok/aria2/BtFileMode;");
	jobject btFileMode = env->GetStaticObjectField(btFileModeClass, btFileModeFieldID);
	return env->NewObject(btMetaInfoDataClass, btMetaInfoDataConstructorID, announceList, env->NewStringUTF(btMetaInfoDataNative.comment.c_str()),
						  env->NewObject(dateClass, dateConstructorID, (jlong) btMetaInfoDataNative.creationDate, btFileMode, env->NewStringUTF(btMetaInfoDataNative.name.c_str())));
}

JNIEXPORT jstring JNICALL Java_com_inchok_aria2_Aria2_getOptionNative(JNIEnv *env, jclass, jlong downloadHandleNative, jstring name) {
	return env->NewStringUTF(((aria2::DownloadHandle *) downloadHandleNative)->getOption(env->GetStringUTFChars(name, JNI_FALSE)).c_str());
}

JNIEXPORT jobject JNICALL Java_com_inchok_aria2_Aria2_getOptionsNative(JNIEnv *env, jclass, jlong downloadHandleNative) {
	aria2::KeyVals optionsNative = ((aria2::DownloadHandle *) downloadHandleNative)->getOptions();
	jclass keyValuesClass = env->FindClass("com/inchok/aria2/KeyValues");
	jmethodID keyValuesConstructorID = env->GetMethodID(keyValuesClass, "<init>", "()V");
	jmethodID keyValuesAddMethodID = env->GetMethodID(keyValuesClass, "add", "(Ljava/lang/Object;)Z");
	jobject options = env->NewObject(keyValuesClass, keyValuesConstructorID);
	jclass pairClass = env->FindClass("com/inchok/aria2/Pair");
	jmethodID pairConstructorID = env->GetMethodID(pairClass, "<init>", "(Ljava/lang/String;Ljava/lang/String;)V");
	for (std::pair<std::string, std::string> &optionNative:optionsNative) {
		jstring key = env->NewStringUTF(optionNative.first.c_str());
		jstring value = env->NewStringUTF(optionNative.second.c_str());
		jobject option = env->NewObject(pairClass, pairConstructorID, key, value);
		env->CallBooleanMethod(options, keyValuesAddMethodID, option);
		env->DeleteLocalRef(key);
		env->DeleteLocalRef(value);
		env->DeleteLocalRef(option);
	}
	return options;
}

JNIEXPORT void JNICALL Java_com_inchok_aria2_Aria2_deleteDownloadHandleNative(JNIEnv *, jclass, jlong downloadHandleNative) {
	aria2::deleteDownloadHandle((aria2::DownloadHandle *) downloadHandleNative);
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_runNative(JNIEnv *, jclass, jlong sessionNative, jint runMode) {
	aria2::RUN_MODE runModeNative = aria2::RUN_DEFAULT;
	switch ((int) runMode) {
		case 0: {
			runModeNative = aria2::RUN_DEFAULT;
			break;
		}
		case 1: {
			runModeNative = aria2::RUN_ONCE;
			break;
		}
		default:;
	}
	return (jint) aria2::run((aria2::Session *) sessionNative, runModeNative);
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_addUriNative(JNIEnv *env, jclass, jlong sessionNative, jlong gid, jobject uris, jobject options, jint position) {
	aria2::A2Gid gidNative = (aria2::A2Gid) gid;
	std::vector<std::string> urisNative;
	jclass listClass = env->FindClass("java/util/List");
	jmethodID listSizeMethodID = env->GetMethodID(listClass, "size", "()I");
	jmethodID listGetMethodID = env->GetMethodID(listClass, "get", "(I)Ljava/lang/Object;");
	for (int i = 0; i < (int) env->CallIntMethod(uris, listSizeMethodID); ++i) {
		jstring uri = (jstring) env->CallObjectMethod(uris, listGetMethodID, (jint) i);
		const char *uriNative = env->GetStringUTFChars(uri, JNI_FALSE);
		urisNative.push_back(uriNative);
		env->ReleaseStringUTFChars(uri, uriNative);
		env->DeleteLocalRef(uri);
	}
	aria2::KeyVals optionsNative;
	jclass keyValuesClass = env->FindClass("com/inchok/aria2/KeyValues");
	jmethodID keyValuesSizeMethodID = env->GetMethodID(keyValuesClass, "size", "()I");
	jmethodID keyValuesGetMethodID = env->GetMethodID(keyValuesClass, "get", "(I)Ljava/lang/Object;");
	jclass pairClass = env->FindClass("com/inchok/aria2/Pair");
	jmethodID pairGetKeyMethodID = env->GetMethodID(pairClass, "getKey", "()Ljava/lang/String;");
	jmethodID pairGetValueMethodID = env->GetMethodID(pairClass, "getValue", "()Ljava/lang/String;");
	for (int i = 0; i < (int) env->CallIntMethod(options, keyValuesSizeMethodID); ++i) {
		jobject option = env->CallObjectMethod(options, keyValuesGetMethodID, (jint) i);
		jstring key = (jstring) env->CallObjectMethod(option, pairGetKeyMethodID);
		jstring value = (jstring) env->CallObjectMethod(option, pairGetValueMethodID);
		const char *keyNative = env->GetStringUTFChars(key, JNI_FALSE);
		const char *valueNative = env->GetStringUTFChars(value, JNI_FALSE);
		optionsNative.push_back(std::pair<std::string, std::string>(keyNative, valueNative));
		env->ReleaseStringUTFChars(key, keyNative);
		env->ReleaseStringUTFChars(value, valueNative);
		env->DeleteLocalRef(key);
		env->DeleteLocalRef(value);
	}
	if (gid == -1) return (jint) aria2::addUri((aria2::Session *) sessionNative, nullptr, urisNative, optionsNative, (int) position);
	else return (jint) aria2::addUri((aria2::Session *) sessionNative, &gidNative, urisNative, optionsNative, (int) position);
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_addMetaLinkNative(JNIEnv *env, jclass, jlong sessionNative, jobject gids, jstring metaLinkFilePath, jobject options, jint position) {
	std::vector<aria2::A2Gid> gidsNative;
	const char *metaLinkFilePathNative = env->GetStringUTFChars(metaLinkFilePath, JNI_FALSE);
	aria2::KeyVals optionsNative;
	jclass keyValuesClass = env->FindClass("com/inchok/aria2/KeyValues");
	jmethodID keyValuesSizeMethodID = env->GetMethodID(keyValuesClass, "size", "()I");
	jmethodID keyValuesGetMethodID = env->GetMethodID(keyValuesClass, "get", "(I)Ljava/lang/Object;");
	jclass pairClass = env->FindClass("com/inchok/aria2/Pair");
	jmethodID pairGetKeyMethodID = env->GetMethodID(pairClass, "getKey", "()Ljava/lang/String;");
	jmethodID pairGetValueMethodID = env->GetMethodID(pairClass, "getValue", "()Ljava/lang/String;");
	for (int i = 0; i < (int) env->CallIntMethod(options, keyValuesSizeMethodID); ++i) {
		jobject option = env->CallObjectMethod(options, keyValuesGetMethodID, (jint) i);
		jstring key = (jstring) env->CallObjectMethod(option, pairGetKeyMethodID);
		jstring value = (jstring) env->CallObjectMethod(option, pairGetValueMethodID);
		const char *keyNative = env->GetStringUTFChars(key, JNI_FALSE);
		const char *valueNative = env->GetStringUTFChars(value, JNI_FALSE);
		optionsNative.push_back(std::pair<std::string, std::string>(keyNative, valueNative));
		env->ReleaseStringUTFChars(key, keyNative);
		env->ReleaseStringUTFChars(value, valueNative);
		env->DeleteLocalRef(key);
		env->DeleteLocalRef(value);
	}
	int result = aria2::addMetalink((aria2::Session *) sessionNative, &gidsNative, metaLinkFilePathNative, optionsNative, (int) position);
	if (gids != nullptr) {
		jclass listClass = env->FindClass("java/util/List");
		jmethodID listAddMethodID = env->GetMethodID(listClass, "add", "(Ljava/lang/Object;)Z");
		jclass gidClass = env->FindClass("com/inchok/aria2/Gid");
		jmethodID gidConstructorID = env->GetMethodID(gidClass, "<init>", "(J)V");
		for (aria2::A2Gid &gidNative:gidsNative) {
			jobject gid = env->NewObject(gidClass, gidConstructorID, (jlong) gidNative);
			env->CallBooleanMethod(gids, listAddMethodID, gid);
			env->DeleteLocalRef(gid);
		}
	}
	return (jint) result;
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_addTorrentNative__JJLjava_lang_String_2Lcom_inchok_aria2_KeyValues_2I(JNIEnv *env, jclass, jlong sessionNative, jlong gid, jstring torrentFilePath, jobject options, jint position) {
	aria2::A2Gid gidNative = (aria2::A2Gid) gid;
	const char *torrentFilePathNative = env->GetStringUTFChars(torrentFilePath, JNI_FALSE);
	aria2::KeyVals optionsNative;
	jclass keyValuesClass = env->FindClass("com/inchok/aria2/KeyValues");
	jmethodID keyValuesSizeMethodID = env->GetMethodID(keyValuesClass, "size", "()I");
	jmethodID keyValuesGetMethodID = env->GetMethodID(keyValuesClass, "get", "(I)Ljava/lang/Object;");
	jclass pairClass = env->FindClass("com/inchok/aria2/Pair");
	jmethodID pairGetKeyMethodID = env->GetMethodID(pairClass, "getKey", "()Ljava/lang/String;");
	jmethodID pairGetValueMethodID = env->GetMethodID(pairClass, "getValue", "()Ljava/lang/String;");
	for (int i = 0; i < (int) env->CallIntMethod(options, keyValuesSizeMethodID); ++i) {
		jobject option = env->CallObjectMethod(options, keyValuesGetMethodID, (jint) i);
		jstring key = (jstring) env->CallObjectMethod(option, pairGetKeyMethodID);
		jstring value = (jstring) env->CallObjectMethod(option, pairGetValueMethodID);
		const char *keyNative = env->GetStringUTFChars(key, JNI_FALSE);
		const char *valueNative = env->GetStringUTFChars(value, JNI_FALSE);
		optionsNative.push_back(std::pair<std::string, std::string>(keyNative, valueNative));
		env->ReleaseStringUTFChars(key, keyNative);
		env->ReleaseStringUTFChars(value, valueNative);
		env->DeleteLocalRef(key);
		env->DeleteLocalRef(value);
	}
	if (gid == -1) return (jint) aria2::addTorrent((aria2::Session *) sessionNative, nullptr, torrentFilePathNative, optionsNative, (int) position);
	else return (jint) aria2::addTorrent((aria2::Session *) sessionNative, &gidNative, torrentFilePathNative, optionsNative, (int) position);
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_addTorrentNative__JJLjava_lang_String_2Ljava_util_List_2Lcom_inchok_aria2_KeyValues_2I(JNIEnv *env, jclass, jlong sessionNative, jlong gid, jstring torrentFilePath, jobject webSeedUris, jobject options, jint position) {
	aria2::A2Gid gidNative = (aria2::A2Gid) gid;
	const char *torrentFilePathNative = env->GetStringUTFChars(torrentFilePath, JNI_FALSE);
	std::vector<std::string> webSeedUrisNative;
	jclass listClass = env->FindClass("java/util/List");
	jmethodID listSizeMethodID = env->GetMethodID(listClass, "size", "()I");
	jmethodID listGetMethodID = env->GetMethodID(listClass, "get", "(I)Ljava/lang/Object;");
	for (int i = 0; i < (int) env->CallIntMethod(webSeedUris, listSizeMethodID); ++i) {
		jstring uri = (jstring) env->CallObjectMethod(webSeedUris, listGetMethodID, (jint) i);
		const char *uriNative = env->GetStringUTFChars(uri, JNI_FALSE);
		webSeedUrisNative.push_back(uriNative);
		env->ReleaseStringUTFChars(uri, uriNative);
		env->DeleteLocalRef(uri);
	}
	aria2::KeyVals optionsNative;
	jclass keyValuesClass = env->FindClass("com/inchok/aria2/KeyValues");
	jmethodID keyValuesSizeMethodID = env->GetMethodID(keyValuesClass, "size", "()I");
	jmethodID keyValuesGetMethodID = env->GetMethodID(keyValuesClass, "get", "(I)Ljava/lang/Object;");
	jclass pairClass = env->FindClass("com/inchok/aria2/Pair");
	jmethodID pairGetKeyMethodID = env->GetMethodID(pairClass, "getKey", "()Ljava/lang/String;");
	jmethodID pairGetValueMethodID = env->GetMethodID(pairClass, "getValue", "()Ljava/lang/String;");
	for (int i = 0; i < (int) env->CallIntMethod(options, keyValuesSizeMethodID); ++i) {
		jobject option = env->CallObjectMethod(options, keyValuesGetMethodID, (jint) i);
		jstring key = (jstring) env->CallObjectMethod(option, pairGetKeyMethodID);
		jstring value = (jstring) env->CallObjectMethod(option, pairGetValueMethodID);
		const char *keyNative = env->GetStringUTFChars(key, JNI_FALSE);
		const char *valueNative = env->GetStringUTFChars(value, JNI_FALSE);
		optionsNative.push_back(std::pair<std::string, std::string>(keyNative, valueNative));
		env->ReleaseStringUTFChars(key, keyNative);
		env->ReleaseStringUTFChars(value, valueNative);
		env->DeleteLocalRef(key);
		env->DeleteLocalRef(value);
	}
	if (gid == -1) return (jint) aria2::addTorrent((aria2::Session *) sessionNative, nullptr, torrentFilePathNative, webSeedUrisNative, optionsNative, (int) position);
	else return (jint) aria2::addTorrent((aria2::Session *) sessionNative, &gidNative, torrentFilePathNative, webSeedUrisNative, optionsNative, (int) position);
}

JNIEXPORT jobject JNICALL Java_com_inchok_aria2_Aria2_getActiveDownloadNative(JNIEnv *env, jclass, jlong sessionNative) {
	jclass listClass = env->FindClass("java/util/ArrayList");
	jmethodID listConstructorID = env->GetMethodID(listClass, "<init>", "()V");
	jobject gids = env->NewObject(listClass, listConstructorID);
	jmethodID listAddMethodID = env->GetMethodID(listClass, "add", "(Ljava/lang/Object;)Z");
	jclass longClass = env->FindClass("java/lang/Long");
	jmethodID longConstructorID = env->GetMethodID(longClass, "<init>", "(J)V");
	for (aria2::A2Gid &gidNative:aria2::getActiveDownload((aria2::Session *) sessionNative)) {
		jobject gid = env->NewObject(longClass, longConstructorID, (jlong) gidNative);
		env->CallBooleanMethod(gids, listAddMethodID, gid);
		env->DeleteLocalRef(gid);
	}
	return gids;
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_removeDownloadNative(JNIEnv *, jclass, jlong sessionNative, jlong gid, jboolean force) {
	return (jint) aria2::removeDownload((aria2::Session *) sessionNative, (aria2::A2Gid) gid, force == JNI_TRUE);
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_pauseDownloadNative(JNIEnv *, jclass, jlong sessionNative, jlong gid, jboolean force) {
	return (jint) aria2::pauseDownload((aria2::Session *) sessionNative, (aria2::A2Gid) gid, force == JNI_TRUE);
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_unpauseDownloadNative(JNIEnv *, jclass, jlong sessionNative, jlong gid) {
	return (jint) aria2::unpauseDownload((aria2::Session *) sessionNative, (aria2::A2Gid) gid);
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_changeOptionNative(JNIEnv *env, jclass, jlong sessionNative, jlong gid, jobject options) {
	aria2::KeyVals optionsNative;
	jclass keyValuesClass = env->FindClass("com/inchok/aria2/KeyValues");
	jmethodID keyValuesSizeMethodID = env->GetMethodID(keyValuesClass, "size", "()I");
	jmethodID keyValuesGetMethodID = env->GetMethodID(keyValuesClass, "get", "(I)Ljava/lang/Object;");
	jclass pairClass = env->FindClass("com/inchok/aria2/Pair");
	jmethodID pairGetKeyMethodID = env->GetMethodID(pairClass, "getKey", "()Ljava/lang/String;");
	jmethodID pairGetValueMethodID = env->GetMethodID(pairClass, "getValue", "()Ljava/lang/String;");
	for (int i = 0; i < (int) env->CallIntMethod(options, keyValuesSizeMethodID); ++i) {
		jobject option = env->CallObjectMethod(options, keyValuesGetMethodID, (jint) i);
		jstring key = (jstring) env->CallObjectMethod(option, pairGetKeyMethodID);
		jstring value = (jstring) env->CallObjectMethod(option, pairGetValueMethodID);
		const char *keyNative = env->GetStringUTFChars(key, JNI_FALSE);
		const char *valueNative = env->GetStringUTFChars(value, JNI_FALSE);
		optionsNative.push_back(std::pair<std::string, std::string>(keyNative, valueNative));
		env->ReleaseStringUTFChars(key, keyNative);
		env->ReleaseStringUTFChars(value, valueNative);
		env->DeleteLocalRef(key);
		env->DeleteLocalRef(value);
	}
	return (jint) aria2::changeOption((aria2::Session *) sessionNative, (aria2::A2Gid) gid, optionsNative);
}

JNIEXPORT jstring JNICALL Java_com_inchok_aria2_Aria2_getGlobalOptionNative(JNIEnv *env, jclass, jlong sessionNative, jstring name) {
	return env->NewStringUTF(aria2::getGlobalOption((aria2::Session *) sessionNative, env->GetStringUTFChars(name, JNI_FALSE)).c_str());
}

JNIEXPORT jobject JNICALL Java_com_inchok_aria2_Aria2_getGlobalOptionsNative(JNIEnv *env, jclass, jlong sessionNative) {
	aria2::KeyVals optionsNative = aria2::getGlobalOptions((aria2::Session *) sessionNative);
	jclass keyValuesClass = env->FindClass("com/inchok/aria2/KeyValues");
	jmethodID keyValuesConstructorID = env->GetMethodID(keyValuesClass, "<init>", "()V");
	jmethodID keyValuesAddMethodID = env->GetMethodID(keyValuesClass, "add", "(Ljava/lang/Object;)Z");
	jobject options = env->NewObject(keyValuesClass, keyValuesConstructorID);
	jclass pairClass = env->FindClass("com/inchok/aria2/Pair");
	jmethodID pairConstructorID = env->GetMethodID(pairClass, "<init>", "(Ljava/lang/String;Ljava/lang/String;)V");
	for (std::pair<std::string, std::string> &optionNative:optionsNative) {
		jstring key = env->NewStringUTF(optionNative.first.c_str());
		jstring value = env->NewStringUTF(optionNative.second.c_str());
		jobject option = env->NewObject(pairClass, pairConstructorID, key, value);
		env->CallBooleanMethod(options, keyValuesAddMethodID, option);
		env->DeleteLocalRef(key);
		env->DeleteLocalRef(value);
		env->DeleteLocalRef(option);
	}
	return options;
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_changeGlobalOptionNative(JNIEnv *env, jclass, jlong sessionNative, jobject options) {
	aria2::KeyVals optionsNative;
	jclass keyValuesClass = env->FindClass("com/inchok/aria2/KeyValues");
	jmethodID keyValuesSizeMethodID = env->GetMethodID(keyValuesClass, "size", "()I");
	jmethodID keyValuesGetMethodID = env->GetMethodID(keyValuesClass, "get", "(I)Ljava/lang/Object;");
	jclass pairClass = env->FindClass("com/inchok/aria2/Pair");
	jmethodID pairGetKeyMethodID = env->GetMethodID(pairClass, "getKey", "()Ljava/lang/String;");
	jmethodID pairGetValueMethodID = env->GetMethodID(pairClass, "getValue", "()Ljava/lang/String;");
	for (int i = 0; i < (int) env->CallIntMethod(options, keyValuesSizeMethodID); ++i) {
		jobject option = env->CallObjectMethod(options, keyValuesGetMethodID, (jint) i);
		jstring key = (jstring) env->CallObjectMethod(option, pairGetKeyMethodID);
		jstring value = (jstring) env->CallObjectMethod(option, pairGetValueMethodID);
		const char *keyNative = env->GetStringUTFChars(key, JNI_FALSE);
		const char *valueNative = env->GetStringUTFChars(value, JNI_FALSE);
		optionsNative.push_back(std::pair<std::string, std::string>(keyNative, valueNative));
		env->ReleaseStringUTFChars(key, keyNative);
		env->ReleaseStringUTFChars(value, valueNative);
		env->DeleteLocalRef(key);
		env->DeleteLocalRef(value);
	}
	return (jint) aria2::changeGlobalOption((aria2::Session *) sessionNative, optionsNative);
}

JNIEXPORT jobject JNICALL Java_com_inchok_aria2_Aria2_getGlobalStatNative(JNIEnv *env, jclass, jlong sessionNative) {
	jclass globalStatClass = env->FindClass("com/inchok/aria2/GlobalStat");
	jmethodID globalStatConstructorID = env->GetMethodID(globalStatClass, "<init>", "(IIIII)V");
	aria2::GlobalStat globalStatNative = aria2::getGlobalStat((aria2::Session *) sessionNative);
	jobject globalStat = env->NewObject(globalStatClass, globalStatConstructorID, globalStatNative.downloadSpeed, globalStatNative.uploadSpeed, globalStatNative.numActive, globalStatNative.numWaiting, globalStatNative.numStopped);
	return globalStat;
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_changePositionNative(JNIEnv *, jclass, jlong sessionNative, jlong gid, jint pos, jint mode) {
	aria2::OffsetMode modeNative = aria2::OFFSET_MODE_SET;
	switch ((int) mode) {
		case 0: {
			modeNative = aria2::OFFSET_MODE_SET;
			break;
		}
		case 1: {
			modeNative = aria2::OFFSET_MODE_CUR;
			break;
		}
		case 2: {
			modeNative = aria2::OFFSET_MODE_END;
			break;
		}
		default:;
	}
	return (jint) aria2::changePosition((aria2::Session *) sessionNative, (aria2::A2Gid) gid, (int) pos, modeNative);
}

JNIEXPORT jint JNICALL Java_com_inchok_aria2_Aria2_shutdownNative(JNIEnv *, jclass, jlong sessionNative, jboolean force) {
	return (jint) aria2::shutdown((aria2::Session *) sessionNative, force == JNI_TRUE);
}

JNIEXPORT jlong JNICALL Java_com_inchok_aria2_Aria2_getDownloadHandleNative(JNIEnv *, jclass, jlong sessionNative, jlong gid) {
	return (jlong) aria2::getDownloadHandle((aria2::Session *) sessionNative, (aria2::A2Gid) gid);
}

#ifdef __cplusplus
}
#endif
