COMPILER=javac
VM=java
SRC=src
TESTS=tests
OUT=build
VENDOR='deps/*:src'

MAIN=trail.Main

OUT_FLAGS=-d $(OUT)
VENDOR_FLAGS=-cp $(VENDOR)
RUN_FLAGS=-cp $(OUT) $(MAIN)

SRC_LIST=@$(OUT)/source_files
TESTS_LIST=@$(OUT)/test_files

list-source-files: ; find $(SRC) -name "*.java" > $(OUT)/source_files
list-test-files: ; find $(TESTS) -name "*.java" > $(OUT)/test_files

# builds source code
all: list-source-files ; $(COMPILER) $(OUT_FLAGS) $(SRC_LIST)

# builds tests
test: list-test-files ; $(COMPILER) $(VENDOR_FLAGS) $(OUT_FLAGS) $(TESTS_LIST)

# runs the project
run: ; $(VM) $(RUN_FLAGS)

# removes the build directory
clean: ; rm -rf $(OUT)/*
