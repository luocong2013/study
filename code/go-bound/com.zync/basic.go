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

func euler() {
	c := 3 + 4i
	fmt.Println(cmplx.Abs(c))
	fmt.Println(cmplx.Pow(math.E, 1i*math.Pi) + 1)
}

func main() {
	fmt.Println("Hello World!")
	variableZeroValue()
	variableInitialValue()
	variableTypeDeduction()
	variableShorter()
	fmt.Println(aa, bb, cc, dd)
	euler()
}
