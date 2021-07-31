#pragma once

#include <stdint.h>
class CAGResourceVisitor
{
public:
	CAGResourceVisitor(void);
	~CAGResourceVisitor(void);

	static BOOL PASCAL SaveResourceToFile(LPCTSTR lpResourceType, WORD wResourceID, LPCTSTR lpFilePath);

	// ��ΪWINDOWS��·����Ŀ¼�ָ������׼��ʽ��һ�£���ת���ᷢ������
	static LPCSTR PASCAL TransWinPathA(LPCSTR lpWinPath, LPSTR lpStandardPath, SIZE_T cchSize);
	static LPCWSTR PASCAL TransWinPathW(LPCWSTR lpWinPath, LPWSTR lpStandardPath, SIZE_T cchSize);

	static int16_t  PASCAL MixerAddS16(int16_t var1, int16_t var2);
	static void PASCAL MixerAddS16(int16_t* src1, const int16_t* src2, size_t size);
};
