package processor.pipeline;

import processor.Processor;

public class InstructionFetch {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performIF()
	{
		if(IF_EnableLatch.isIF_enable())

		{
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			
			if(EX_IF_Latch.isbranch == false)
			{
				containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			}
				
			else{
				containingProcessor.getRegisterFile().setProgramCounter(currentPC + EX_IF_Latch.inc);
				System.out.println("got a branch" + "   " + Integer.toString(EX_IF_Latch.inc));
				EX_IF_Latch.isbranch = false;
			}
			System.out.println("CPC:"+containingProcessor.getRegisterFile().getProgramCounter());
			int newInstruction = containingProcessor.getMainMemory().getWord(containingProcessor.getRegisterFile().getProgramCounter());
			IF_OF_Latch.setInstruction(newInstruction);
			
			
			IF_EnableLatch.setIF_enable(false);
			IF_OF_Latch.setOF_enable(true);
		}
	}

}
