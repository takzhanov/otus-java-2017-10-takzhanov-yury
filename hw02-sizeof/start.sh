#!/usr/bin/env bash
#default -XX:+UseCompressedOops, ссылки по 4 байта
java -javaagent:target/hw02-sizeof.jar -jar target/hw02-sizeof.jar

#большие ссылки по 8 байт
#java -XX:-UseCompressedOops -javaagent:target/hw02-sizeof.jar -jar target/hw02-sizeof.jar
