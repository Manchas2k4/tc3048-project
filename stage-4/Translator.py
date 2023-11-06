from Environment import *
from Type import *
import turtle

class Node:
	def __init__(self, line = None):
		self._line = line

	def eval(self, env, aTurtle):
		pass

class Numeric(Node):
	def __init__(self, line = None):
		super().__init__(line)

	def eval(self, env, aTurtle):
		pass

class Logic(Node):
	def __init__(self, line = None):
		super().__init__(line)

	def eval(self, env, aTurtle):
		pass

class Character(Node):
	def __init__(self, line = None):
		super().__init__(line)

	def eval(self, env, aTurtle):
		pass

class Void(Node):
	def __init__(self, line = None):
		super().__init__(line)

	def eval(self, env, aTurtle):
		pass

#--------------------------- Numeric Subclasses ---------------------------#
class Number(Numeric):
	def __init__(self, value, line = None):
		super().__init__(line)
		self.__value = value

	def eval(self, env, aTurtle):
		return self.__value

class Identifier(Numeric):
	def __init__(self, name, line):
		super().__init__(line)
		self.__name = name

	def eval(self, env, aTurtle):
		result = env.lookup(self.__name)
		if result != None:
			(_, value) = result
			return value
		else:
			text = 'Line ' + self._line + " - " + self.__name + " has not been declared"
			raise Exception(text)
	
class Minus(Numeric):
	def __init__(self, right, line = None):
		super().__init__(line)
		self.__right = right

	def eval(self, env, aTurtle):
		return -1 * float(self._right.eval(env, aTurtle))
	
class Add(Numeric):
	def __init__(self, left, right, line = None):
		super().__init__(line)
		self.__left = left
		self.__right = right

	def eval(self, env, aTurtle):
		l = float(self.__left.eval(env, aTurtle))
		r = float(self.__right.eval(env, aTurtle))
		return (l + r)
		
class Subtrat(Numeric):
	pass
		
class Multiply(Numeric):
	pass
	
class Divide(Numeric):
	def __init__(self, left, right, line):
		super().__init__(line)
		self.__left = left
		self.__right = right

	def eval(self, env, aTurtle):
		l = float(self.__left.eval(env, aTurtle))
		r = float(self.__right.eval(env, aTurtle))
		if r != 0:
			return (l / r)
		else:
			text = 'Line ' + self._line + " - division by zero"
			raise Exception(text)
		
class Module(Numeric):
	pass
#--------------------------- Numeric Subclasses ---------------------------#

#--------------------------- Logic Subclasses ---------------------------#
class Boolean(Logic):
	def __init__(self, value, line = None):
		super().__init__(line)
		self.__value = value

	def eval(self, env, aTurtle):
		return self.__value
	
class Not(Logic):
	def __init__(self, right, line = None):
		super().__init__(line)
		self.__right = right

	def eval(self, env, aTurtle):
		return not(bool(self.__right.eval(env, aTurtle)))
		
class GraterOrEqual(Logic):
	def __init__(self, left, right, line = None):
		super().__init__(line)
		self.__left = left
		self.__right = right

	def eval(self, env, aTurtle):
		l = float(self.__left.eval(env, aTurtle))
		r = float(self.__right.eval(env, aTurtle))
		return (l >= r)
	
class Grater(Logic):
	pass
	
class LesserOrEqual(Logic):
	pass
	
class Lesser(Logic):
	pass
	
class Equal(Logic):
	pass
	
class Different(Logic):
	pass
	
class And(Logic):
	def __init__(self, left, right, line = None):
		super().__init__(line)
		self.__left = left
		self.__right = right

	def eval(self, env, aTurtle):
		l = bool(self.__left.eval(env, aTurtle))
		r = bool(self.__right.eval(env, aTurtle))
		return (l and r)

class Or(Logic):
	pass
#--------------------------- Logic Subclasses ---------------------------#

#-------------------------- Character Subclasses ---------------------------#
class String(Character):
	def __init__(self, value, line = None):
		super().__init__(line)
		self.__value = value

	def eval(self, env, aTurtle):
		return self.__value
	
class ElementList(Character):
	def __init__(self, element, elementList = None, line = None):
		super().__init__(line)
		self.__element = element
		self.__elementList = elementList

	def eval(self, env, aTurtle):
		result = ""

		if isinstance(self.__element, String):
			result = self.__element.eval(env, aTurtle)
		else:
			result = str(self.__element.eval(env, aTurtle))

		if self.__elementList != None:
			result = result + " " + self.__elementList.eval(env, aTurtle)
		return result
#-------------------------- Character Subclasses ---------------------------#

#---------------------------- Void Subclasses -----------------------------#
class If(Void):
	def __init__(self, condition, sequence, line = None):
		super().__init__(line)
		self.__condition = condition
		self.__sequence = sequence

	def eval(self, env, aTurtle):
		if bool(self.__condition.eval(env, aTurtle)):
			env = Environment(env)
			self.__sequence.eval(env, aTurtle)
			env = env.getPrevious()

class IfElse(Void):
	pass

class While (Void):
	def __init__(self, __condition, sequence, line = None):
		super().__init__(line)
		self.__condition = condition
		self.__sequence = sequence

	def eval(self, env, aTurtle):
		pass
	
class Print(Void):
	pass

class PenWidth(Void):
	pass

class Color(Void):
	def __init__(self, redExpression, greenExpression, blueExpression, line = None):
		super().__init__(line)
		self.__redExpression = redExpression
		self.__greenExpression = greenExpression
		self.__blueExpression = blueExpression

	def eval(self, env, aTurtle):
		red = int(self.__redExpression.eval(env, aTurtle))
		if red < 0 or red > 255:
			text = 'Line ' + self._line + "expected a positive number between 0..255 in the red value."
			raise Exception(text)
		green = int(self.__greenExpression.eval(env, aTurtle))
		if green < 0 or green > 255:
			text = 'Line ' + self._line + "expected a positive number between 0..255 in the blue value."
			raise Exception(text)
		blue = int(self.__blueExpression.eval(env, aTurtle))
		if blue < 0 or blue > 255:
			text = 'Line ' + self._line + "expected a positive number between 0..255 in the green value."
			raise Exception(text)
		aTurtle.pencolor(red, green, blue)

class PenDown(Void):
	def __init__(self, line = None):
		super().__init__(line)

	def eval(self, env, aTurtle):
		aTurtle.pendown()

class PenUp(Void):
	pass

class Arc(Void):
	def __init__(self, ratioExpression, angleExpression, line = None):
		super().__init__(line)
		self.__ratioExpression = ratioExpression
		self.__angleExpression = angleExpression

	def eval(self, env, aTurtle):
		ratio = int(self.__ratioExpression.eval(env, aTurtle))
		angle = int(self.__angleExpression.eval(env, aTurtle))
		aTurtle.circle(ratio, angle)

class Circle(Void):
	pass

class Clear(Void):
	pass

class SetXY(Void):
	pass

class SetX(Void):
	pass

class SetY(Void):
	pass

class Left(Void):
	pass

class Right(Void):
	pass

class Backward(Void):
	pass

class Forward(Void):
	pass

class Home(Void):
	pass

class Assigment(Void):
	pass
		
class IdDeclaration(Void):
	pass

class idDeclarationList(Void):
	pass

class Declaration(Void):
	pass

class StatementSequence(Void):
	pass

class Program(Void):
	pass