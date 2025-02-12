package processor.pipeline;

import java.util.HashMap;
import java.util.Map;

import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			Map<String, String> map = new HashMap<>();
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

			//TODO
			int instruction = IF_OF_Latch.getInstruction();
			
			//System.out.println("INT-" + instruction);
			String ins = Integer.toBinaryString(instruction);
			while(ins.length() < 32)
			{
				ins = "0" + ins;
			}
			String opc = ins.substring(0, 5);
			String opt = map.get(opc);
			OF_EX_Latch.ins = opt;
			System.out.println(opt);
			//System.out.println("INS HWHOLE "+ins);
			if(opt == "load" || opt == "store" || opt == "addi" || opt == "subi"  || opt == "muli"  || opt == "divi" || opt == "andi" || opt == "ori" || opt == "xori" || opt == "slti" || opt == "slli" || opt == "srli" || opt == "srai")
			{
				String r1s = ins.substring(5,10);
				System.out.println(" R1 "+r1s);
				String r2s = ins.substring(10,15);
				System.out.println(" R2 "+r2s);
				String imms = ins.substring(15, 32);
				System.out.println("IMDsdsds IMM "+imms);
				OF_EX_Latch.r1 = Integer.parseInt(r1s,2);
				OF_EX_Latch.r2 = Integer.parseInt(r2s,2);
				OF_EX_Latch.imm = Integer.parseInt(imms,2);

			}
			else if(opt == "add" || opt == "sub"  || opt == "mul"  || opt == "div" || opt == "and" || opt == "or" || opt == "xor" || opt == "slt" || opt == "sll" || opt == "srl" || opt == "sra")
			{
				String r1s = ins.substring(5,10);
				System.out.println("JHFJKHDKJSHKJ R1 "+r1s);
				String r2s = ins.substring(10,15);
				System.out.println("JHFJKHDKJSHKJ R2 "+r2s);
				String r3s = ins.substring(15, 20);
				System.out.println("JHFJKHDKJSHKJ R3 "+r3s);
				OF_EX_Latch.r1 = Integer.parseInt(r1s,2);
				OF_EX_Latch.r2 = Integer.parseInt(r2s,2);
				OF_EX_Latch.r3 = Integer.parseInt(r3s,2);

			}
			else if(opt == "beq"|| opt == "bne"|| opt == "blt"|| opt == "bgt")
			{
				String r1s = ins.substring(5,10);
				System.out.println("R1 "+r1s);
				String r2s = ins.substring(10,15);
				System.out.println("R2 "+r2s);
				String imms = ins.substring(15, 32);
				System.out.println("IMDsdsds IMM "+imms);
				OF_EX_Latch.r1 = Integer.parseInt(r1s,2);
				OF_EX_Latch.r2 = Integer.parseInt(r2s,2);
				int decimal = (int) Long.parseLong(imms, 2);
					if (imms.charAt(0) == '1') { // Check if it's negative
						decimal -= (1L << 17); // Subtract 2^32 to get the correct negative value
					}
				System.out.println("DECIMAL:" + Integer.toString(decimal));
				OF_EX_Latch.imm = decimal;
			}
			else if(opt == "jmp")
			{
				String imms = ins.substring(10, 32);
				int decimal = (int) Long.parseLong(imms, 2);
					if (imms.charAt(0) == '1') { // Check if it's negative
						decimal -= (1L << 22); // Subtract 2^32 to get the correct negative value
					}
				System.out.println("DECIMAL:" + Integer.toString(decimal));
				OF_EX_Latch.imm = decimal;
			}
			
			
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}
	}

}
