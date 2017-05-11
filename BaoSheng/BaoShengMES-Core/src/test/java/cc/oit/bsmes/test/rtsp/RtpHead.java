package cc.oit.bsmes.test.rtsp;

public class RtpHead
{
	// 2. RTP数据包头格式：（ 字节长度为12个字节）
	/* byte 0 */
	public int cc; // :4; /* CSRC count */
	public int x; // :1; /* header extension flag */
	public int p; // :1; /* padding flag */
	public int version; // version :2; /* protocol version */
	/* byte 1 */
	public int pt; // :7; /* payload type */ //pt说明： 96=>H.264,
					// 97=>G.726,8=>G.711a, 100=>报警数据
	public int marker; // :1; /* marker bit */
	/* bytes 2, 3 */
	public int seqno; // :16; /* sequence number */
	/* bytes 4-7 */
	public int ts; /* timestamp in ms */
	/* bytes 8-11 */
	public int ssrc; /* synchronization source */

	public void setRtpHead(char[] b)
	{
		char[] tsarray = new char[4];
		if (b.length == 12)
		{
			// cc = ((temp[0] & 0xf0) >> 4);
			// x = ((temp[0] & 0x08) >> 3);
			// p = ((temp[0] & 0x04) >> 2);
			// version = (temp[0] & 0x03);
			pt = (int) (b[1] & 0x7f); // 获得byte字节的前7位 /* payload
			System.arraycopy(b, 4, tsarray, 0, 4);// type */
			ts = CharsToInt(tsarray);
		} else
		{
			System.out.println("******  数据错误 3    ******");
		}

	}

	public int CharsToInt(char[] buf)
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
