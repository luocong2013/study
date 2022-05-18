package main

import (
	"fmt"
	"math"
	"math/cmplx"
)

/**
 * Go 变量类型:
 * bool, string
 * (u)int, (u)int8, (u)int16, (u)int32, (u)int64, uintptr
 * byte, rune
 * float32, float64, complex64, complex128
 *
 * u 表示无符号
 * ptr 表示指针
 * rune 表示java中的char
 * complex 表示复数, 由实部+虚部组成, 如: 3+4i
 */

var aa = 3
var bb = "kkk"

var (
	cc = 4
	dd = "aas"
)

func variableZeroValue() {
	var a int
	var s string
	fmt.Printf("%d %q\n", a, s)
}

func variableInitialValue() {
	var a, b int = 3, 4
	var s string = "abc"
	fmt.Println(a, b, s)
}

func variableTypeDeduction() {
	var a, b, c, s = 3, 4, true, "def"
	fmt.Println(a, b, c, s)
}

func variableShorter() {
	a, b, c, s := 3, 4, true, "def"
	fmt.Println(a, b, c, s)
}

/**
 * 复数
 */
func euler() {
	c := 3 + 4i
	fmt.Println(cmplx.Abs(c))
	fmt.Println(cmplx.Pow(math.E, 1i*math.Pi) + 1)
	fmt.Println(cmplx.Exp(1i*math.Pi) + 1)
	fmt.Printf("%.3f\n", cmplx.Exp(1i*math.Pi)+1)
}

/**
 * 类型强制转换
 */
func triangle() {
	var a, b int = 3, 4
	var c int
	c = int(math.Sqrt(float64(a*a + b*b)))
	fmt.Println(c)
}

/**
 * 常量
 */
func consts() {
	const filename = "abc.txt"
	const a, b = 3, 4
	var c int
	c = int(math.Sqrt(a*a + b*b))
	const (
		d, e = 7, 9
	)
	fmt.Println(filename, c, d, e)
}

/**
 * 枚举类型
 * iota 实现自增值
 */
func enums() {
	const (
		cpp = iota
		_
		java
		python
		golang
	)

	const (
		b = 1 << (10 * iota)
		kb
		mb
		gb
		tb
		pb
	)
	fmt.Println(cpp, java, python, golang)
	fmt.Println(b, kb, mb, gb, tb, pb)
}

func main() {
	fmt.Println("Hello World!")
	variableZeroValue()
	variableInitialValue()
	variableTypeDeduction()
	variableShorter()
	fmt.Println(aa, bb, cc, dd)
	euler()
	triangle()
	consts()
	enums()
}
