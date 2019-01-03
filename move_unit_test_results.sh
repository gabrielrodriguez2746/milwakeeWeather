mkdir ~/unit_test_results/
find . -name "*index.html" -print0 | while IFS= read -r -d $'\0' file; do
    cp -R --parents $(dirname "$file") ~/unit_test_results/
done
