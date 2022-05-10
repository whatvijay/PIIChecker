Java , Spring based tool which can be accessed via web page .It accepts log file as input analyzes and delivers any PII info in logs as output.

1)  File is accepted as input by spring controller(launch http://localhost:9010/Index.html)
2) File is converted to a string or string array
3) NLP is used to analyze data.
4) PII info found is returned
5) NLP internally uses few NLP models . They can be replaced with customized ones as required.

Ensure Java 8 on class path and run PiicheckerinlogsApplication