package cc.oit.bsmes.test.rtsp;

public class RtspToH264DataPrase
{
	private final int MAX_VIDEO_FRAME = 1280 * 720;
	private final int RECV_BUFFER_SIZE = 1024 * 256;
	private char[] OneFrameH264Data = new char[MAX_VIDEO_FRAME];
	private int g_ts = 0;
	private int g_tsLen = 0;
	static int g_nBufLen = 0;
	int g_nNeedLen = 0;
	int g_nFullPackLen = 0;
	int g_nCopyLen = 0;
	int nRemain1 = 0;
	int nRemain2 = 0;

	int PT_H264 = 96;// 视频数据
	int packet_len;
	int streamType;
	int nCopy;

	int ts;
	int nVideoLen;

	RtspHead RSTP_HEAD = new RtspHead();
	char[] VideoData = new char[RECV_BUFFER_SIZE];
	char[] rtsphead = new char[20];
	int N_H_S = 8;
	int N_RTP_HDR = 12;

	char[] pTemp1 = new char[RECV_BUFFER_SIZE];
	char[] pTemp2 = new char[RECV_BUFFER_SIZE];
	char[] data = new char[MAX_VIDEO_FRAME];

	RtpHead rtphead = new RtpHead();
	char[] HeadTemp = new char[N_RTP_HDR];

	public int PraseRcvVideoData(char[] msg, int length)
	{
		// System.out.println("****************拆包数据长度：****" + length);
		int i = 0;
		if (length < 20)
		{
			System.out.println("*******  数据错误1 *******");
		}
		g_nCopyLen = 0;
		g_nFullPackLen = 0;
		g_nNeedLen = 0;
		nRemain1 = 0;
		nRemain2 = 0;
		g_nBufLen = length;
		if (g_nNeedLen == 0)
		{
			System.arraycopy(msg, 0, rtsphead, 0, 20);
			RSTP_HEAD.setRtspHead(rtsphead);
			packet_len = RSTP_HEAD.payloadLen; // 获得有效数据包的长度，nonth1();

			if (packet_len > 0 && RSTP_HEAD.daollar == 0x24)
			{
				g_nFullPackLen = packet_len;
				// 当前包不够一个nalu包
				if (g_nBufLen < (g_nFullPackLen + N_H_S) && g_nBufLen >= N_H_S)
				{
					nCopy = g_nBufLen - N_H_S;
					System.arraycopy(msg, N_H_S, VideoData, 0, nCopy);
					g_nCopyLen = nCopy;
					g_nBufLen = 0;
					if (g_nFullPackLen - nCopy > 0)
					{
						g_nNeedLen = g_nFullPackLen - nCopy;
					}
				} else
				// >= 单个nalu包
				{
					nCopy = g_nFullPackLen;
					System.arraycopy(msg, N_H_S, VideoData, 0, nCopy);
					g_nCopyLen = nCopy;
					g_nNeedLen = 0;
					// ================================================
					// FULL PACK
					i = RecvPackData(VideoData, nCopy);
					nRemain1 = g_nBufLen - (g_nFullPackLen + N_H_S);
					g_nBufLen = 0;
					if (nRemain1 > 0)
					{
						System.arraycopy(msg, g_nFullPackLen + N_H_S, pTemp1,
								0, nRemain1);
						PraseRcvVideoData(pTemp1, nRemain1);
					}
				}
			} else
			{
				g_nCopyLen = 0;
				g_nBufLen = 0;
				g_nFullPackLen = 0;
				g_nNeedLen = 0;
				nRemain1 = 0;
				nRemain2 = 0;
			}
		}
		if (g_nNeedLen > 0)
		{
			if (g_nBufLen < g_nNeedLen)
			{
				nCopy = g_nBufLen;
				System.arraycopy(msg, 0, VideoData, g_nCopyLen, nCopy);
				g_nCopyLen = g_nCopyLen + nCopy;
				g_nNeedLen = g_nNeedLen - nCopy;
				g_nBufLen = 0;
			} else
			{
				nCopy = g_nNeedLen;
				System.arraycopy(msg, 0, VideoData, g_nCopyLen, nCopy);
				g_nCopyLen = g_nCopyLen + nCopy;
				g_nNeedLen = 0;
				// ================================================
				// FULL PACK
				i = RecvPackData(VideoData, g_nFullPackLen);
				nRemain2 = g_nBufLen - nCopy;
				g_nBufLen = 0;
				if (nRemain2 > 0)
				{
					System.arraycopy(msg, nCopy, pTemp2, 0, nRemain2);
					PraseRcvVideoData(pTemp2, nRemain2);
				}
			}
		}
		return i;
	}

	public int RecvPackData(char[] msg, int length)
	{
		int i = 0;
		if (length < N_RTP_HDR)
		{
			System.out.println("*******  数据错误2 *******");
		}
		System.arraycopy(msg, 0, HeadTemp, 0, N_RTP_HDR);
		rtphead.setRtpHead(HeadTemp);
		ts = rtphead.ts;
		if (rtphead.pt == PT_H264)
		{
			nVideoLen = length - N_RTP_HDR;
			if (ts != g_ts && g_ts > 0)
			{
				// 在此处将OneFrameH264Data丢给解码器解码显示（先判断是否为空）
				System.arraycopy(OneFrameH264Data, 0, data, 0, g_tsLen);
				if (data != null)
				{
					System.out
							.println("  开始解码  --DecodeH264-- 长度：： " + g_tsLen);
//					synchronized (this)
//					{
						// i = HttpJniNative.DecodeH264(getBytes(data), g_tsLen); // JNI
//					}

					System.out.println("  开始解码  --DecodeH264-- OK!!!! ");
				}
				g_tsLen = 0;
				System.arraycopy(msg, N_RTP_HDR, OneFrameH264Data, g_tsLen,
						nVideoLen);
				g_tsLen += nVideoLen;
				g_ts = ts;
			} else
			{
				System.arraycopy(msg, N_RTP_HDR, OneFrameH264Data, g_tsLen,
						nVideoLen);
				g_tsLen += nVideoLen;
				g_ts = ts;
			}
		}
		return i;
	}

	// char转byte
	public byte[] getBytes(char[] chars)
	{
		byte[] cb = new byte[chars.length];
		for (int i = 0; i < chars.length; i++)
		{
			cb[i] = (byte) chars[i];
		}
		System.gc();
		return cb;
	}
}
