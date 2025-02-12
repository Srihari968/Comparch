	.data
a:
	11
	.text
main:
	load %x0, $a, %x1
	divi %x1, 2, %x2
	beq  %x0, %x31, even
	bne  %x0, %x31, odd
	end
even:
	subi %x0, 1, %x10
	end
odd:
	addi %x0, 1, %x10
	end
