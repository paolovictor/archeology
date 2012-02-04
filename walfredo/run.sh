if [ ! -e ".preprocessed" ]; then
    echo "Preprocessing game animations and levels. This may take some time..."
    ./preprocess_game_data.sh > preprocess.log

    echo "Done!"
    touch .preprocessed
fi

java -cp walfredo.jar Rodar
