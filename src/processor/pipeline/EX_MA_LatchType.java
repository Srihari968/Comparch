package processor.pipeline;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	String ins;
	int val;
	int rd, rs;
	int memAdd;

	int rn1 = -1;
	int rn2 = -1;
	int rn3 = -1;
	
	public EX_MA_LatchType()
	{
		MA_enable = false;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}

}
