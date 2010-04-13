if [ $1 ]; then
    pwd="-p"
else
    pwd=""
fi

mysql -u root $pwd < create_db.sql
mysql -u root $pwd < insert_test_values.sql