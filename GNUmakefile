#
# Simple Makefile for creating jygrads.jar and tests.
#
# If this GNUmakefile seems odd to you it is probably because I don't 
# know about the standard way of doing this in Java. If you know of a
# better way, drop a note at opengrads-devel@lists.sf.net. This is a hack.
#

VERSION = 1.2.0

JAVAC = javac
JAVA = java
JAR = jar

JYTHON = $(JAVA) -jar Jython/jython.jar

JASOURCES := $(wildcard org/opengrads/interfaces/*.java) \
             $(wildcard org/opengrads/core/*.java) \
             $(wildcard org/opengrads/factory/*.java) 

JYSOURCES := $(wildcard Lib/grads/*.py) 

JACLASSES :=  $(addsuffix .class, $(basename $(JASOURCES)))  

JYCLASSES :=  $(addsuffix '$$'py.class, $(basename $(JYSOURCES)))  
JARSTUFF := $(JASOURCES) $(JACLASSES) Lib Matlab tests

all : jygrads.jar jygrads-superpack.jar

jygrads.jar : $(JACLASSES) .jyclasses
	@/bin/rm -rf jygrads.jar
	@$(JAR) cvf jygrads.jar $(JARSTUFF)

jygrads-superpack.jar : Jython/jython.jar $(JACLASSES) .jyclasses
	/bin/cp Jython/jython.jar jygrads-superpack.jar
	@$(JAR) uvf jygrads-superpack.jar $(JARSTUFF)

help: $(JACLASSES)
	@echo ""
	@echo "Java classes: "
	@echo "------------"
	@/bin/ls -1 $(JACLASSES)
	@echo ""
	@echo "Jython classes: "
	@echo "--------------"
	@/bin/ls -1 $(JYCLASSES)
	@echo ""

%.class : %.java
	$(JAVAC) -cp .:Jython/jython.jar $*.java

.jyclasses : Jython/jython.jar $(JYSOURCES)
	(cd Lib; $(JAVA) -jar ../Jython/jython.jar -c 'import grads')
	@touch .jyclasses

test: jygrads-superpack.jar tests/TestJyGrADS.class tests/TestGrads.class
	$(JAVA) -cp .:./jygrads-superpack.jar:./tests TestJyGrADS
	$(JAVA) -cp .:./jygrads-superpack.jar:./tests TestGrads

dist: 
	@echo "Creating source distribution"
	@$(MAKE) sandbox
	@(cd .build;  tar cvfz jygrads-$(VERSION)-src.tar.gz jygrads-$(VERSION))
	@(cd .build;  zip -r   jygrads-$(VERSION)-src.zip    jygrads-$(VERSION))
	@/bin/rm -rf .build/jygrads-$(VERSION)
	@/bin/mv .build dist
	@$(MAKE) all
	@/bin/cp jygrads.jar           dist/jygrads-$(VERSION).jar
	@/bin/cp jygrads-superpack.jar dist/jygrads-$(VERSION)-superpack.jar

sandbox:
	@$(MAKE) distclean
	@mkdir -p    .build/jygrads-$(VERSION)
	@cp -r *     .build/jygrads-$(VERSION)
	@/bin/rm -rf `find .build/jygrads-$(VERSION) -name CVS -print`

clean:
	@find . -name '*~' -exec /bin/rm '{}' \;
	@find . -name '*.class' -exec /bin/rm '{}' \;
	@/bin/rm -rf .jyclasses *.tmp

distclean:
	@$(MAKE) clean
	@/bin/rm -rf *.jar dist .build



