package org.lb.plc.ads;

import java.util.Arrays;
import org.lb.plc.Toolbox;

public class ReadWriteResponse extends Payload {
	private final long errorCode;
	private final long length;
	private final byte[] payload;

	public long getLength() {
		return length;
	}

	public byte[] getPayload() {
		return payload;
	}

	public ReadWriteResponse(final long errorCode, final long length,
			final byte[] payload) {
		this.errorCode = errorCode;
		this.length = length;
		this.payload = payload;
	}

	public static Payload valueOf(final byte[] data) {
		final long errorCode = Toolbox.bytesToUint32(data, 0);
		final long length = Toolbox.bytesToUint32(data, 4);
		final byte[] payload = Arrays.copyOfRange(data, 8, data.length);
		return new ReadWriteResponse(errorCode, length, payload);
	}

	@Override
	public org.lb.plc.ams.ErrorCode getErrorCode() {
		return org.lb.plc.ams.ErrorCode.valueOf(errorCode);
	}

	@Override
	public int getCommandId() {
		return 9;
	}

	@Override
	public byte[] toBinary() {
		final int dataLength = 8 + payload.length;
		byte[] ret = new byte[dataLength];
		Toolbox.copyIntoByteArray(ret, 0, Toolbox.uint32ToBytes(errorCode));
		Toolbox.copyIntoByteArray(ret, 4, Toolbox.uint32ToBytes(length));
		Toolbox.copyIntoByteArray(ret, 8, payload);
		return ret;
	}

	@Override
	public String toString() {
		return String.format("AdsReadWriteResponse: Error code: %d, "
				+ "Length: %d, Read: %s", errorCode, length, Arrays
				.toString(payload));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (errorCode ^ (errorCode >>> 32));
		result = prime * result + (int) (length ^ (length >>> 32));
		result = prime * result + Arrays.hashCode(payload);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ReadWriteResponse))
			return false;
		ReadWriteResponse other = (ReadWriteResponse) obj;
		if (errorCode != other.errorCode)
			return false;
		if (length != other.length)
			return false;
		if (!Arrays.equals(payload, other.payload))
			return false;
		return true;
	}
}