#pragma once

class CExtendVideoFrameObserver :
	public agora::media::IVideoFrameObserver
{
public:
	CExtendVideoFrameObserver();
	~CExtendVideoFrameObserver();

	virtual bool onCaptureVideoFrame(VideoFrame& videoFrame);
	virtual bool onRenderVideoFrame(unsigned int uid, VideoFrame& videoFrame);

private:
	LPBYTE				m_lpImageBuffer;
	LPBYTE				m_lpImageBufferRemote;
	LPBYTE				m_lpImageBufferRemoteTemp;
	LPBYTE				m_lpY;
	LPBYTE				m_lpU;
	LPBYTE				m_lpV;

	FILE* m_pFileYUVLocal;
	//CMutex m_LocalMutex;
};

