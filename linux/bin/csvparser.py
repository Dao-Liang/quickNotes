#!/usr/bin/python
def parser(filename,out):
	#titles=()
	records={}
	
	fieldcount=0	
	file=open(filename)
	
	#record count
	rcount=0
	for line in file:
		
		if "==" in line:
			rcount+=1

		if ':' in line:
			values=line.split(':')
			title=values[0].strip().lower()
			value=values[1].strip('\n').lower()
			if title not in records:
				records[title]={rcount:value}
				fieldcount+=1
			else:
				records[title][rcount]=value


			
	for r in records:
		print r, records[r]
			
	outf=open(out,'w')

	c=0
	max_f_len=0
	"write field name"
	for k in records:
		if max_f_len<len(records[k]):
			max_f_len=len(records[k])
		if c<=fieldcount:
			print k,';',
			outf.write(k+';')
			c+=1
		else:
			print k,'\n'	
			outf.write(k+';')

	outf.write('\n')

	"write data"
	for rc in range(max_f_len):
		
		for k in records:
			
			if rc in records[k]:
				print records[k][rc],';',
				outf.write(records[k][rc]+';')
			else:
				print ' ;',
				outf.write(' ;')
		print '\n'
		outf.write('\n')
				
	outf.close()
		
'''
	rlen=len(records)
	count=0
	
	maxlen=0
	outf = open(out,'w')
	for record in records:
		count+=1
		if maxlen<len(records[record]):
			maxlen=len(records[record])
		if count<rlen:
			print record,";",
			outf.write(record+' ; ')
		else:
			print record,
			outf.write(record+'  ')

	outf.write('\n')
	
	count=0

	for col in range(maxlen):
		for record in records:
			count+=1
			if col<len(records[record]):
				
				if count<rlen:	
					print records[record][col],' ; ',
					if records[record][col]  is not None:
						outf.write(records[record][col]+';')
					else:
						outf.write(' ;')
				else:
					print records[record][col]+'\n' ,
					if records[record][col] is not None:
						outf.write(records[record][col]+'\n')
					else:
						outf.write(' \n')
					count=0
		#print '\n'
		#outf.write('\n')

'''	

if __name__=='__main__':
	import sys
	inf='input.txt'
	outf='out.txt'
	if len(sys.argv)==2:
		inf=sys.argv[1]
	elif len(sys.argv)==3:
		inf=sys.argv[1]
		outf=sys.argv[2]
	parser(inf,outf)
			
			
			

		
