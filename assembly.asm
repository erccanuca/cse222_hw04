li $t0,4			#a=4 in $t0.

li $t1,34			#b=34 in $t1.

li $t3,5			#d=5 in $t3.
div $t0,$t1
mflo $t2
mult $t2,$t3
mflo $t3

move $a0,$t2		#Move from $t2 to $a0.
li $v0,1			#Load $v0=1         .
syscall				#Print result.

