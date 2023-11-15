from Lexer import *
from Parser import *
import sys

if __name__ == '__main__':
	parserTree = None

	parser = Parser("test_cases/bad/prog7.txt")
	parserTree = parser.analize()

	if parserTree != None:
		parserTree.eval()
