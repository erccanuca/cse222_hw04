li $t0,4			#a=4 in $t0.

li $t1,3			#b=3 in $t1.

li $t2,5			#d=5 in $t2.
div $t0,$t1
mflo $t2
mult $t2,$t3
mflo $t2

move $a0,$t2		#Move from $t2 to $a0.
li $v0,1			#Load $v0=1         .
syscall				#Print result.

