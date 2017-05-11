package cc.oit.bsmes.test.rtsp;

public class RtspHead
{ /* rtsp head 包头长度为20个字节 */
	public char daollar; /* 8, $:dollar sign(24 decimal) */
	public char channelid; /* 8, channel id */
	public int resv; /* 16, reseved */
	public int payloadLen; /* 32, payload length */

	public void setRtspHead(char[] b)
	{
		char[] rtsp = new char[8];
		char[] payload = new char[4];
		System.arraycopy(b, 0, rtsp, 0, 8);
		daollar = rtsp[0];
		channelid = rtsp[1];
		System.arraycopy(rtsp, 4, payload, 0, 4);
		payloadLen = Chars4ToInt(payload); // 计算有效数据的长度 ？？？字节序计算
	}

	public int Chars4ToInt(char[] buf)
	{
		int firstByte = 0;
		int secondByte = 0;
		int thirdByte = 0;
		int fourthByte = 0;
		int index = 0;
		firstByte = (0x000000FF & ((int) buf[index]));
		secondByte = (0x000000FF & ((int) buf[index + 1]));
		thirdByte = (0x000000FF & ((int) buf[index + 2]));
		fourthByte = (0x000000FF & ((int) buf[index + 3]));
		return ((int) (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte)) & 0xFFFFFFFF;
	}

}