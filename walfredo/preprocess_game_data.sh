rm -rf data/fases/*.evt
rm -rf data/gfx/*.ani

java -cp walfredo.jar creators.FaseCreator data/fases/fase1_defs.txt data/fases/fase1.evt
java -cp walfredo.jar creators.FaseCreator data/fases/fase2_defs.txt data/fases/fase2.evt
java -cp walfredo.jar creators.FaseCreator data/fases/fase3_defs.txt data/fases/fase3.evt

java -cp walfredo.jar creators.AniCreator data/gfx/bola_menu/bola_menu_anims.txt data/gfx/bola_menu.ani data/gfx/bola_menu
java -cp walfredo.jar creators.AniCreator data/gfx/bully1/bully1_anims.txt data/gfx/bully1.ani data/gfx/bully1
java -cp walfredo.jar creators.AniCreator data/gfx/bully2/bully1_anims.txt data/gfx/bully2.ani data/gfx/bully2
java -cp walfredo.jar creators.AniCreator data/gfx/bully3/bully1_anims.txt data/gfx/bully3.ani data/gfx/bully3
java -cp walfredo.jar creators.AniCreator data/gfx/bully4/bully1_anims.txt data/gfx/bully4.ani data/gfx/bully4
java -cp walfredo.jar creators.AniCreator data/gfx/bully1_defunto/bully1_defunto_anims.txt data/gfx/bully1_defunto.ani data/gfx/bully1_defunto
java -cp walfredo.jar creators.AniCreator data/gfx/bully2_defunto/bully1_defunto_anims.txt data/gfx/bully2_defunto.ani data/gfx/bully2_defunto
java -cp walfredo.jar creators.AniCreator data/gfx/bully3_defunto/bully1_defunto_anims.txt data/gfx/bully3_defunto.ani data/gfx/bully3_defunto
java -cp walfredo.jar creators.AniCreator data/gfx/bully4_defunto/bully1_defunto_anims.txt data/gfx/bully4_defunto.ani data/gfx/bully4_defunto
java -cp walfredo.jar creators.AniCreator data/gfx/walfredo/walfredo_anims.txt data/gfx/walfredo.ani data/gfx/walfredo
java -cp walfredo.jar creators.AniCreator data/gfx/walfredo_defunto/walfredo_defunto_anims.txt data/gfx/walfredo_defunto.ani data/gfx/walfredo_defunto
