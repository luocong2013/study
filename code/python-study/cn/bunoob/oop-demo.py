# Python面向对象编程
# 类(Class): 用来描述具有相同的属性和方法的对象的集合。它定义了该集合中每个对象所共有的属性和方法。对象是类的实例。
# 类变量：类变量在整个实例化的对象中是公用的。类变量定义在类中且在函数体之外。类变量通常不作为实例变量使用。
# 数据成员：类变量或者实例变量用于处理类及其实例对象的相关的数据。
# 方法重写：如果从父类继承的方法不能满足子类的需求，可以对其进行改写，这个过程叫方法的覆盖（override），也称为方法的重写。
# 实例变量：定义在方法中的变量，只作用于当前实例的类。
# 继承：即一个派生类（derived class）继承基类（base class）的字段和方法。继承也允许把一个派生类的对象作为一个基类对象对待。例如，一个Dog类型的对象派生自Animal类，这是模拟"是一个（is-a）"。
# 实例化：创建一个类的实例，类的具体对象。
# 方法：类中定义的函数。
# 对象：通过类定义的数据结构实例。对象包括两个数据成员（类变量和实例变量）和方法。
# 和其它编程语言相比，Python 在尽可能不增加新的语法和语义的情况下加入了类机制。
#
# Python中的类提供了面向对象编程的所有基本功能：类的继承机制允许多个基类，派生类可以覆盖基类中的任何方法，方法中可以调用基类中的同名方法。
# 对象可以包含任意数量和类型的数据。

class MyClass:
    """一个简单的类实例"""

    # __init__()方法用来初始化状态，类似于java中的构造方法
    # 类定义了 __init__() 方法的话，类的实例化操作会自动调用 __init__() 方法
    def __init__(self, realpart):
        self.i = realpart

    def f(self):
        return 'hello world'

    # self代表类的实例，而非类，类方法必须包含参数 self, 且为第一个参数
    def prt(self):
        print(self)
        print(self.__class__)


# 实例化类
x = MyClass(20)
# 访问类的属性和方法
print('MyClass 类的属性 i 为：', x.i)
print('MyClass 类的类方法 f 输出为：', x.f())
x.prt()


# 类定义
class people:
    # 定义基本属性
    name = ''
    age = 0
    # 定义私有属性，私有属性在类外部无法直接进行访问
    __weigth = 0

    # 定义构造方法
    def __init__(self, n, a, w):
        self.name = n
        self.age = a
        self.__weigth = w

    def speak(self):
        print("{0} 说：我 {1:d} 岁。".format(self.name, self.age))


# 实例化类
p = people('runoob', 10, 30)
p.speak()


# 继承（允许多个基类,使用逗号隔开）

# 单继承示例
class student(people):
    grade = ''

    def __init__(self, n, a, w, g):
        # 调用父类的构造函数
        people.__init__(self, n, a, w)
        self.grade = g

    # 重写父类的方法
    def speak(self):
        print("{0} 说：我 {1:d} 岁了，我在读 {2:d} 年级".format(self.name, self.age, self.grade))


s = student('ken', 10, 60, 3)
s.speak()


# 多继承
# 需要注意圆括号中父类的顺序，若是父类中有相同的方法名，而在子类使用时未指定，python从左至右搜索 即方法在子类中未找到时，从左到右查找父类中是否包含方法
# 另一个类，多重继承之前的准备
class speaker():
    topic = ''
    name = ''

    def __init__(self, n, t):
        self.name = n
        self.topic = t

    def speak(self):
        print("我叫 {0}，我是一个演说家，我演讲的主题是 {1}".format(self.name, self.topic))


class sample(speaker, student):
    a = ''

    def __init__(self, n, a, w, g, t):
        student.__init__(self, n, a, w, g)
        speaker.__init__(self, n, t)


test = sample("Tim", 25, 80, 4, "Python")
test.speak()  # 方法名同，默认调用的是在括号中排前地父类的方法


# 类的私有属性
# __private_attrs：两个下划线开头，声明该属性为私有，不能在类地外部被使用或直接访问。
# 在类内部的方法中使用时 self.__private_attrs。

# 类的方法
# 在类地内部，使用 def 关键字来定义一个方法，与一般函数定义不同，类方法必须包含参数 self，且为第一个参数，self 代表的是类的实例。
# self 的名字并不是规定死的，也可以使用 this，但是最好还是按照约定是用 self。

# 类的私有方法
# __private_method：两个下划线开头，声明该方法为私有方法，只能在类的内部调用 ，不能在类地外部调用。self.__private_methods。

class Site:
    def __init__(self, name, url):
        self.name = name  # 公开变量
        self.__url = url  # 私有变量

    def who(self):
        print('name: ', self.name)
        print('url: ', self.__url)

    def __foo(self):  # 私有方法
        print('这是私有方法')

    def foo(self):  # 公共方法
        print('这是公共方法')
        self.__foo()


site = Site('菜鸟教程', 'http://www.runoob.com/')
site.who()
site.foo()
# site.__foo()  # 报错



# 运算符重载
class Vector:
    def __init__(self, a, b):
        self.a = a
        self.b = b

    def __str__(self):
        return 'Vector ({0:d}, {1:d})'.format(self.a, self.b)

    def __add__(self, other):
        return Vector(self.a + other.a, self.b + other.b)

v1 = Vector(2, 10)
v2 = Vector(5, -2)
print(v1 + v2)

