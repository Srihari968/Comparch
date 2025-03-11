package processor.pipeline;

import java.text.NumberFormat.Style;
import java.util.HashMap;
import java.util.Map;

import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_enableLatch;
	int rn1= -1, rn2 = -1, rn3 = -1;	
	Map<String, String> map = new HashMap<>();
	int instruction;
	String ins;
	

	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType IF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_enableLatch = IF_EnableLatch;
		map.put("00000", "add");
			map.put("00001", "addi");
			map.put("00010", "sub");
			map.put("00011", "subi");
			map.put("00100", "mul");
			map.put("00101", "muli");
			map.put("00110", "div");
			map.put("00111", "divi");
			map.put("01000", "and");
			map.put("01001", "andi");
			map.put("01010", "or");
			map.put("01011", "ori");
			map.put("01100", "xor");
			map.put("01101", "xori");
			map.put("01110", "slt");
			map.put("01111", "slti");
			map.put("10000", "sll");
			map.put("10001", "slli");
			map.put("10010", "srl");
			map.put("10011", "srli");
			map.put("10100", "sra");
			map.put("10101", "srai");
			map.put("11000", "jmp");
			map.put("11001", "beq");
			map.put("11010", "bne");
			map.put("11011", "blt");
			map.put("11100", "bgt");
			map.put("10110", "load");
			map.put("10111", "store");
			map.put("11101", "end");
	}
	
	public void performOF()
	{
		
		
		instruction = IF_OF_Latch.getInstruction();
		if(instruction == 0)
		{
			System.out.println("OF------------null");
			return;
		}
			
			////System.out.println("INT-" + instruction);
			ins = Integer.toBinaryString(instruction);
			while(ins.length() < 32)
			{
				ins = "0" + ins;
			}
			String opc = ins.substring(0, 5);
			String opt = map.get(opc);
			System.out.println("OF------------" + opt);
		if(IF_OF_Latch.isOF_enable())
		{
			
			//TODO
			
			RegisterFile regs = containingProcessor.getRegisterFile();
			
			
			int rn1= -1, rn2 = -1, rn3 = -1;
			////System.out.println("INS HWHOLE "+ins);
			if(opt == "load" || opt == "store" || opt == "addi" || opt == "subi"  || opt == "muli"  || opt == "divi" || opt == "andi" || opt == "ori" || opt == "xori" || opt == "slti" || opt == "slli" || opt == "srli" || opt == "srai")
			{
				String r1s = ins.substring(5,10);
				//System.out.println(" R1 "+r1s);
				String r2s = ins.substring(10,15);
				//System.out.println(" R2 "+r2s);
				String imms = ins.substring(15, 32);
				//System.out.println("IMDsdsds IMM "+imms);

				
				OF_EX_Latch.r1 = regs.getValue(Integer.parseInt(r1s,2));
				OF_EX_Latch.r2 = (Integer.parseInt(r2s,2));
				OF_EX_Latch.imm = Integer.parseInt(imms,2);
				OF_EX_Latch.r3 = -1;

				rn1 = Integer.parseInt(r1s,2);
				rn2 = -1;
				rn3 = Integer.parseInt(r2s,2);

				

			}
			else if(opt == "add" || opt == "sub"  || opt == "mul"  || opt == "div" || opt == "and" || opt == "or" || opt == "xor" || opt == "slt" || opt == "sll" || opt == "srl" || opt == "sra")
			{
				String r1s = ins.substring(5,10);
				//System.out.println("JHFJKHDKJSHKJ R1 "+r1s);
				String r2s = ins.substring(10,15);
				//System.out.println("JHFJKHDKJSHKJ R2 "+r2s);
				String r3s = ins.substring(15, 20);
				//System.out.println("JHFJKHDKJSHKJ R3 "+r3s);
				OF_EX_Latch.r1 =regs.getValue(Integer.parseInt(r1s,2));
				OF_EX_Latch.r2 = regs.getValue(Integer.parseInt(r2s,2));
				OF_EX_Latch.r3 = (Integer.parseInt(r3s,2));
				rn1 = Integer.parseInt(r1s,2);
				rn2 = Integer.parseInt(r2s,2);
				rn3 = Integer.parseInt(r3s,2);

			}
			else if(opt == "beq"|| opt == "bne"|| opt == "blt"|| opt == "bgt")
			{
				String r1s = ins.substring(5,10);
				//System.out.println("R1 "+r1s);
				String r2s = ins.substring(10,15);
				//System.out.println("R2 "+r2s);
				String imms = ins.substring(15, 32);
				//System.out.println("IMDsdsds IMM "+imms);
				OF_EX_Latch.r1 = regs.getValue(Integer.parseInt(r1s,2));
				OF_EX_Latch.r2 = regs.getValue(Integer.parseInt(r2s,2));
				int decimal = (int) Long.parseLong(imms, 2);
					if (imms.charAt(0) == '1') { // Check if it's negative
						decimal -= (1L << 17); // Subtract 2^32 to get the correct negative value
					}
				//System.out.println("DECIMAL:" + Integer.toString(decimal));
				OF_EX_Latch.imm = decimal;

				rn1 = Integer.parseInt(r1s,2);
				rn2 = Integer.parseInt(r2s,2);
				rn3 = -1;
			}
			else if(opt == "jmp")
			{
				String imms = ins.substring(10, 32);
				int decimal = (int) Long.parseLong(imms, 2);
					if (imms.charAt(0) == '1') { // Check if it's negative
						decimal -= (1L << 22); // Subtract 2^32 to get the correct negative value
					}
				//System.out.println("DECIMAL:" + Integer.toString(decimal));
				rn1 = -1;
				rn2 = -1;
				rn3 = -1;
				OF_EX_Latch.imm = decimal;
			}

			//int colli = 0;

			String previns = OF_EX_Latch.ins;
			System.out.println("PREVINS "+previns);
			Execute ex = containingProcessor.getEXUnit();
			MemoryAccess ma = containingProcessor.getMAUnit();
			RegisterWrite rw = containingProcessor.getRWUnit();

			boolean colli1 = checkColli(ex.rn1, ex.rn2, ex.rn3, rn1, rn2, rn3, ex.opt , opt);
			if(colli1)
			{
				System.out.println("COLLISSION DETECTED 1");
			}

			boolean colli2 = checkColli(ma.rn1, ma.rn2, ma.rn3, rn1, rn2, rn3, ma.opt, opt);
			if(colli2)
			{
				System.out.println("COLLISSION DETECTED 2");
			}

			boolean colli3 = checkColli(rw.rn1, rw.rn2, rw.rn3, rn1, rn2, rn3, rw.opt, opt);
			if(colli3)
			{
				System.out.println("COLLISSION DETECTED 3");
			}

			if(colli1 || colli2 || colli3)
			{
				IF_enableLatch.setIF_enable(false);
				OF_EX_Latch.ins =null;
				return;
			}
			else
			{
				IF_enableLatch.setIF_enable(true);
			}



			OF_EX_Latch.rn1 = rn1;
			OF_EX_Latch.rn2 = rn2;
			OF_EX_Latch.rn3 = rn3;
			OF_EX_Latch.ins = opt;
			
			
			//IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}
	}

	boolean checkColli(int r11, int r12, int r13, int r21, int r22, int r23, String op1, String op2)
	{
		int src1 = r21;
		int src2 = r22;

		int dest = r13;
		System.out.println("OP1 "+op1);
		System.out.println("OP2 "+op2);
		System.out.println("SRC1 "+src1);
		System.out.println("SRC2 "+src2);
		System.out.println("DEST "+dest);

		if(op2 == "jmp")
		{
			return false;
		}

		if(op1 == "store" || op1 == "jmp" || op1 == "beq" || op1 == "bgt" || op1 == "bne" || op1 == "blt" || op1 == null)
		{
			return false;
		}

		

		if((src1 == dest && dest != -1) || (src2 == dest && dest != -1))
		{
			return true;
		}
		if(op1 == "div" || op1 == "divi")
		{
			if(src1 == 31 || src2 == 31)
			{
				return true;
			}
		}


		return false;
	}
	

}
