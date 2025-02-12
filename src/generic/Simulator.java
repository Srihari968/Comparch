package generic;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import processor.Clock;
import processor.Processor;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		
		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile)
	{
		/*
		 * TODO
		 * 1. load the program into memory according to the program layout described
		 *    in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 *     x0 = 0
		 *     x1 = 65535
		 *     x2 = 65535
		 */
		try{
			InputStream inputStream = new FileInputStream(assemblyProgramFile);
			byte[] buffer = new byte[4];
			long data;
			data = inputStream.read(buffer);
			int i = 0;
			int value = ((buffer[0] & 0xFF) << 24) | 
                            ((buffer[1] & 0xFF) << 16) |
                            ((buffer[2] & 0xFF) << 8)  |
                            (buffer[3] & 0xFF);
				System.out.println(value);
			processor.getRegisterFile().setProgramCounter(value-1);
			while((data = inputStream.read(buffer)) != -1)
			{
				 value = ((buffer[0] & 0xFF) << 24) | 
                            ((buffer[1] & 0xFF) << 16) |
                            ((buffer[2] & 0xFF) << 8)  |
                            (buffer[3] & 0xFF);
				processor.getMainMemory().setWord(i, value);
				i++;
			}
			processor.getRegisterFile().setValue(0, 0);
			processor.getRegisterFile().setValue(1, 65535);
			processor.getRegisterFile().setValue(2, 65535);


			inputStream.close();
			System.out.println(processor.getMainMemory().getContentsAsString(0, 10));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

	}
	
	public static void simulate()
	{
		int mi = 100000, ic = 0;
		while(simulationComplete == false)
		{
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			processor.getEXUnit().performEX();
			Clock.incrementClock();
			processor.getMAUnit().performMA();
			Clock.incrementClock();
			processor.getRWUnit().performRW();
			Clock.incrementClock();
			ic++;
			if(ic == mi)
			{
				break;
			}
		}
		
		// TODO
		// set statistics
		processor.getRegisterFile().setProgramCounter(processor.getRegisterFile().getProgramCounter()+1);
		Statistics sta = new Statistics();
		sta.setNumberOfInstructions(ic);
		sta.setNumberOfCycles(ic);
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
