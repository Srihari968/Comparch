package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performRW()
	{
		if(MA_RW_Latch.isRW_enable())
		{
			//TODO
			int rd = MA_RW_Latch.rd;
			int val = MA_RW_Latch.val;
			if(MA_RW_Latch.ins == "end")
			{
				Simulator.setSimulationComplete(true);
			}
			//if(MA_RW_Latch.ins == "bgt" || MA_RW_Latch.ins == "beq" || MA_RW_Latch.ins == "bne" || MA_RW_Latch.ins == "blt" || MA_RW_Latch.ins == "jmp" || MA_RW_Latch.ins == "store" || MA_RW_Latch.ins == "end")
			if(MA_RW_Latch.rd == -1)
			{
				MA_RW_Latch.setRW_enable(false);
				IF_EnableLatch.setIF_enable(true);
				return;
			}
			System.out.println("Setting " + val + " in " + rd);
			containingProcessor.getRegisterFile().setValue(rd, val);
			
			// if instruction being processed is an end instruction, remember to call Simulator.setSimulationComplete(true);
			
			
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
		}
	}

}
