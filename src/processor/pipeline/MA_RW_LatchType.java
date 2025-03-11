package processor.pipeline;

public class MA_RW_LatchType {
	
	boolean RW_enable;
	String ins;
	int val;
	int rd, rs;
	int memAdd;

	int rn1 = -1;
	int rn2 = -1;
	int rn3 = -1;
	
	public MA_RW_LatchType()
	{
		RW_enable = false;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}

}
