//
// Created by john on 2020-02-12.
//

#include <stdio.h>
#include "lame.h"

#ifndef LAME_MP3_ENCODE_H
#define LAME_MP3_ENCODE_H
#ifdef __cplusplus
extern "C" {
#endif

class Mp3Encoder {
private:
    FILE *pcmFIle;
    FILE *mp3File;
    lame_t lameClient;

public:
    Mp3Encoder();

    ~Mp3Encoder();

    int lint(const char *pcmFilePath,
             const char *mp3FilePath,
             int sampleRate,
             int channels,
             int bitRate);

    void Encode();

    void Destory();
};

#ifdef __cplusplus
}
#endif
#endif //LAME_MP3_ENCODE_H





