package processor.pipeline;

import processor.Clock;
import processor.Processor;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	int rn1= -1, rn2 = -1, rn3 = -1;
	String opt;
	Processor processor;
	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		processor = containingProcessor;
	}
	
	public void performEX()
	{
		//TODO
		int rd, rs, rs2, rs1, imm;
		EX_MA_Latch.ins = OF_EX_Latch.ins;
		RegisterFile regs = processor.getRegisterFile();
		opt = OF_EX_Latch.ins;

		System.out.println("EX------------" + OF_EX_Latch.ins);
		EX_MA_Latch.rd = -1;
		EX_MA_Latch.val = 0;
		if(OF_EX_Latch.ins == null)
		{
			EX_MA_Latch.ins = null;
			EX_MA_Latch.rn1 = -1;
			EX_MA_Latch.rn2 = -1;
			EX_MA_Latch.rn3 = -1;
			return;
		}


		

		rn1 = OF_EX_Latch.rn1;
		rn2 = OF_EX_Latch.rn2;
		rn3 = OF_EX_Latch.rn3;

		EX_MA_Latch.rn1 = OF_EX_Latch.rn1;
		EX_MA_Latch.rn2 = OF_EX_Latch.rn2;
		EX_MA_Latch.rn3 = OF_EX_Latch.rn3;

		
		int res = 0;
		if(OF_EX_Latch.ins != null)
		switch (OF_EX_Latch.ins) {
			case "add":
				////System.out.println("OP IS ADD");
				rs1 = OF_EX_Latch.r1;
				rs2 = OF_EX_Latch.r2;
				rd = OF_EX_Latch.r3;
				//res = regs.getValue(rs1) + regs.getValue(rs2);
				res = rs1 + rs2;
				EX_MA_Latch.rd = rd;
				//regs.setValue(2, 3);
				//regs.setValue(rd, regs.getValue(rs1) + regs.getValue(rs2));
				break;
			case "addi":
				//System.out.println("OP IS ADDI");
				rs1 = OF_EX_Latch.r1;
				rd = OF_EX_Latch.r2;
				imm = OF_EX_Latch.imm;
				//res = regs.getValue(rs1) + imm;
				res = rs1 + imm;
				EX_MA_Latch.rd = rd;

				//regs.setValue(rd, regs.getValue(rs1) + imm);

				break;
			case "sub":
				//System.out.println("OP IS SUB");
				rs1 = OF_EX_Latch.r1;
				rs2 = OF_EX_Latch.r2;
				rd = OF_EX_Latch.r3;
				//res = regs.getValue(rs1) - regs.getValue(rs2);
				res = rs1 - rs2;
				EX_MA_Latch.rd = rd;
				//regs.setValue(rd, regs.getValue(rs1) - regs.getValue(rs2));
				break;
			case "subi":
				//System.out.println("OP IS SUBI");
				rs1 = OF_EX_Latch.r1;
				rd = OF_EX_Latch.r2;
				imm = OF_EX_Latch.imm;
				//regs.setValue(rd, regs.getValue(rs1) - imm);
				//regs.setValue(rd, regs.getValue(rs1) - imm);
				//res = regs.getValue(rs1) - imm;
				res = rs1 - imm;
				EX_MA_Latch.rd = rd;

				break;
			case "mul":
				//System.out.println("OP IS MUL");
				rs1 = OF_EX_Latch.r1;
				rs2 = OF_EX_Latch.r2;
				rd = OF_EX_Latch.r3;
				//res = regs.getValue(rs1) * regs.getValue(rs2);
				res = rs1 * rs2;
				EX_MA_Latch.rd = rd;
				//regs.setValue(rd, regs.getValue(rs1) * regs.getValue(rs2));
				break;
			case "muli":
				//System.out.println("OP IS MULI");
				rd = OF_EX_Latch.r2;
				rs = OF_EX_Latch.r1;
				imm = OF_EX_Latch.imm;
				//res = regs.getValue(rs) * imm;
				res = rs * imm;
				//processor.getRegisterFile().setValue(rd, processor.getRegisterFile().getValue(rs)*imm);
				EX_MA_Latch.rd = rd;
				//regs.setValue(rd, regs.getValue(rs) * imm);
				break;
			case "div":
				//System.out.println("OP IS DIV");
				rs1 = OF_EX_Latch.r1;
				rs2 = OF_EX_Latch.r2;
				rd = OF_EX_Latch.r3;
				// res = regs.getValue(rs1) / regs.getValue(rs2);
				res = rs1 / rs2;
				EX_MA_Latch.rd = rd;
				System.out.println("Setting "+rs1%rs2 + " in 31");
				//regs.setValue(rd, regs.getValue(rs1) / regs.getValue(rs2));
				regs.setValue(31, (rs1) % (rs2));
				//System.out.println("31-----------------"+regs.getValue(31));
				break;
			case "divi":
				//System.out.println("OP IS DIVI");
				rd = OF_EX_Latch.r2;
				rs = OF_EX_Latch.r1;
				imm = OF_EX_Latch.imm;
				//System.out.println("---------DIVI----------------");
				//System.out.println(rd + " " + rs + " " + imm);
				//System.out.println("-----------------------------");
				//int sval = regs.getValue(rs);
				int sval = rs;
				//processor.getRegisterFile().setValue(rd, sval/imm);
				EX_MA_Latch.rd = rd;
				res = sval/imm;
				System.out.println("Setting "+ sval%imm + " in 31");
				regs.setValue(31, sval%imm);
				////System.out.println("31-----------------"+regs.getValue(31)+"RS-----------"+regs.getValue(rs));
				break;
			case "and":
				//System.out.println("OP IS AND");
				rs1 = OF_EX_Latch.r1;
				rs2 = OF_EX_Latch.r2;
				rd = OF_EX_Latch.r3;
				//regs.setValue(rd, regs.getValue(rs1) & regs.getValue(rs2));
				//res = regs.getValue(rs1) & regs.getValue(rs2);
				res = rs1 & rs2;
				EX_MA_Latch.rd = rd;
				
				break;
			case "andi":
				rd = OF_EX_Latch.r2;
				rs = OF_EX_Latch.r1;
				imm = OF_EX_Latch.imm;
				//System.out.println("OP IS ANDI");
				EX_MA_Latch.rd = rd;
				// res = regs.getValue(rs) & imm;
				res = rs & imm;
				break;
			case "or":
				//System.out.println("OP IS OR");
				rs1 = OF_EX_Latch.r1;
				rs2 = OF_EX_Latch.r2;
				rd = OF_EX_Latch.r3;
				//res = regs.getValue(rs1) | regs.getValue(rs2);
				res = rs1 | rs2;
				EX_MA_Latch.rd = rd;
				//regs.setValue(rd, regs.getValue(rs1) | regs.getValue(rs2));
				break;
			case "ori":
				//System.out.println("OP IS ORI");
				rs1 = OF_EX_Latch.r1;
				rd = OF_EX_Latch.r2;
				imm = OF_EX_Latch.imm;
				//regs.setValue(rd, regs.getValue(rs1) | imm);
				EX_MA_Latch.rd = rd;
				//res = regs.getValue(rs1) |  imm;
				res = rs1 | imm;
				break;
			case "xor":
				//System.out.println("OP IS XOR");
				rs1 = OF_EX_Latch.r1;
				rs2 = OF_EX_Latch.r2;
				rd = OF_EX_Latch.r3;
				//res = regs.getValue(rs1) ^ regs.getValue(rs2);
				res = rs1 ^ rs2;
				EX_MA_Latch.rd = rd;
				//regs.setValue(rd, regs.getValue(rs1) ^ regs.getValue(rs2));
				break;
			case "xori":
				//System.out.println("OP IS XORI");
				rs1 = OF_EX_Latch.r1;
				rd = OF_EX_Latch.r2;
				imm = OF_EX_Latch.imm;
				//regs.setValue(rd, regs.getValue(rs1) ^ imm);
				EX_MA_Latch.rd = rd;
				//res = regs.getValue(rs1) ^ imm;
				res = rs1 ^ imm;
				break;
			case "slt":
				//System.out.println("OP IS SLT");
				rs1 = OF_EX_Latch.r1;
				rs2 = OF_EX_Latch.r2;
				rd = OF_EX_Latch.r3;
				res = (regs.getValue(rs1) < regs.getValue(rs2))? 1:0;
				EX_MA_Latch.rd = rd;
				//regs.setValue(rd, (regs.getValue(rs1) < regs.getValue(rs2))? 1:0);
				break;
			case "slti":
				//System.out.println("OP IS SLTI");
				rs1 = OF_EX_Latch.r1;
				rd = OF_EX_Latch.r2;
				imm = OF_EX_Latch.imm;
				res = rs1 < imm? 1:0;
				EX_MA_Latch.rd = rd;
				regs.setValue(rd, (regs.getValue(rs1) < imm)? 1:0);

				break;
			case "sll":
				//System.out.println("OP IS SLL");
				rs1 = OF_EX_Latch.r1;
				rs2 = OF_EX_Latch.r2;
				rd = OF_EX_Latch.r3;
				//res = regs.getValue(rs1) << regs.getValue(rs2);
				res = rs1 << rs2;

				EX_MA_Latch.rd = rd;
				//regs.setValue(rd, regs.getValue(rs1) << regs.getValue(rs2));

				break;
			case "slli":
				//System.out.println("OP IS SLLI");
				rs1 = OF_EX_Latch.r1;
				rd = OF_EX_Latch.r2;
				imm = OF_EX_Latch.imm;
				//regs.setValue(rd, regs.getValue(rs1) << imm);
				EX_MA_Latch.rd = rd;
				//res = regs.getValue(rs1) << imm;
				res = rs1 << imm;
				
				break;
			case "srl":
				//System.out.println("OP IS SRL");
				rs1 = OF_EX_Latch.r1;
				rs2 = OF_EX_Latch.r2;
				rd = OF_EX_Latch.r3;
				//regs.setValue(rd, regs.getValue(rs1) >> regs.getValue(rs2));
				//res = regs.getValue(rs1) >> regs.getValue(rs2);
				res = rs1 >> rs2;
				EX_MA_Latch.rd = rd;
				break;
			case "srli":
				//System.out.println("OP IS SRLI");
				break;
			case "sra":
				//System.out.println("OP IS SRA");
				rs1 = OF_EX_Latch.r1;
				rs2 = OF_EX_Latch.r2;
				rd = OF_EX_Latch.r3;
				//regs.setValue(rd, regs.getValue(rs1) >>> regs.getValue(rs2));
				//res = regs.getValue(rs1) >>> regs.getValue(rs2);
				res = rs1 >>> rs2;
				EX_MA_Latch.rd = rd;
				break;
			case "srai":
				//System.out.println("OP IS SRAI");
				break;
			case "jmp":
				//System.out.println("OP IS JMP");
				imm = OF_EX_Latch.imm;
				EX_IF_Latch.isbranch = true;
				EX_IF_Latch.inc = imm;
				return;
				
			case "beq":
				//System.out.println("OP IS BEQ");
				rs1 = OF_EX_Latch.r1;
				rs2 = OF_EX_Latch.r2;
				imm = OF_EX_Latch.imm;
				if(rs1 == rs2)
				{
					EX_IF_Latch.isbranch = true;
					EX_IF_Latch.inc = imm;
					containingProcessor.getIF_EnableLatch().setIF_enable(false);
					containingProcessor.getIFUnit().IF_OF_Latch.setOF_enable(false);
					containingProcessor.getOFUnit().ins = null;
					containingProcessor.getOFUnit().instruction = 0;
					//containingProcessor.IF
					return;
				
				}
				break;
			case "bne":
				//System.out.println("OP IS BNE");
				rs1 = OF_EX_Latch.r1;
				rs2 = OF_EX_Latch.r2;
				imm = OF_EX_Latch.imm;
				if(rs1 != rs2)
				{
					EX_IF_Latch.isbranch = true;
					EX_IF_Latch.inc = imm;
					containingProcessor.getOFUnit().ins = null;
					containingProcessor.getOFUnit().instruction = 0;
					return;
					
				}
				break;
			case "blt":
				//System.out.println("OP IS BLT");
				rs1 = OF_EX_Latch.r1;
				rs2 = OF_EX_Latch.r2;
				imm = OF_EX_Latch.imm;
				if(rs1 <  rs2)
				{
					EX_IF_Latch.isbranch = true;
					EX_IF_Latch.inc = imm;
					containingProcessor.getOFUnit().ins = null;
					containingProcessor.getOFUnit().instruction = 0;
					return;
					
				}
				break;
			case "bgt":
				//System.out.println("OP IS BGT");
				rs1 = OF_EX_Latch.r1;
				rs2 = OF_EX_Latch.r2;
				imm = OF_EX_Latch.imm;
				if(rs1 > rs2)
				{
					EX_IF_Latch.isbranch = true;
					EX_IF_Latch.inc = imm;
					containingProcessor.getOFUnit().ins = null;
					containingProcessor.getOFUnit().instruction = 0;
					return;		
				}
				break;
			case "load":
				//System.out.println("OP IS LOAD");
				rd = OF_EX_Latch.r2;
				rs = OF_EX_Latch.r1;
				imm = OF_EX_Latch.imm;
				//regs.setValue(rd,processor.getMainMemory().getWord(imm + processor.getRegisterFile().getValue(rs)));
				EX_MA_Latch.rd = rd;
				//EX_MA_Latch.memAdd = imm + regs.getValue(rs);
				EX_MA_Latch.memAdd = rs + imm;

				break;
			case "store":
				//System.out.println("OP IS STORE");
				rd = OF_EX_Latch.r2;
				rs = OF_EX_Latch.r1;
				imm = OF_EX_Latch.imm;
				//processor.getMainMemory().setWord(regs.getValue(rd)+imm, regs.getValue(rs));
				EX_MA_Latch.rs = rs;
				EX_MA_Latch.memAdd = regs.getValue(rd)+imm;


				break;
			case "end":
				//System.out.println("OP IS END");
				break;
			default:
				//System.out.println("Unknown Operation");
				break;
		}
		EX_MA_Latch.val = res;
		
		//OF_EX_Latch.setEX_enable(false);
		EX_MA_Latch.setMA_enable(true);
	}

}
