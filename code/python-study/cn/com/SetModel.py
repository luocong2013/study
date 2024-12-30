student = {"Tom", "Jim", "Mark", "Tom", "Jack", "Rose"}

print(student)

if 'Rose' in student:
    print("Rose在集合中")
else:
    print("Rose不在集合中")

s = set([1, 1, 2, 2, 3, 3])
print(s)
s.add(5)
print(s)
s.remove(3)
print(s)

a = set("abracadabra")
b = set("alacazam")

print(a)
print(b)

print(a - b)

print(a | b)

print(a & b)

print(a ^ b)
