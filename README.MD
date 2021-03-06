WARESTORE PROJECT

Kiskereskedelmi cégek raktározási, számlázási, HR és webshop alkalmazásának első sprintje.

Sprint cél: Raktározási és HR funkcionalitás alapjainak megteremtése.

FUTTATÁS:

        Windows: run_app.bat

        Linux: run_app.sh

        indítása.
        Ezután elérhetőek a végpontok a localhost:8080-as portján.
        Az egyszerűbb ellenőrzés érdekében a project mappájában megtalálható egy
    
        Warestore_Collection.postman_collection.json

        nevű fájl. Melyet Postman-be importálva gyorsan és egyszerűen
        indíthatjuk és ellenőrizhetjük az összes végpont működését.

TESZTELÉS:
    
    Windows: Command Promptból docker_run_tests_on_db.bat indítása.

    Linux: docker_run_tests_on_db.sh indítása.

    A tesztelefedettség kiértékeléséről a target/site/jacoco/index.html-ből
    lehet értesülni. (Figyelem: A százalékok nem egyeznek az IntellIJ 
    run tests with coverage százalékos eredményével!)

    Igény szerinti IntellIJ-ből való teszteléshez csak adatbázis indítása:
        
        Windows: run_db.bat

        Linux: run_db.sh
    

VÉGPONTOK:

    PRODUCT:
        POST: /product - új Product hozzáadás 
                         Requestbody: ProductDTO
        GET: /product - összes Product kilistázása
        GET: /product/name/{name} - adott nevű Product-ok listáját adja vissza
        GET: /product/type/{type} - adott típusú Product-okat listázza
                                    type: ProductType enum megfelelő String változata
        GET: /product/status/{status} - adott státuszban lévő product-okat listázza
                                    status: ProductStatus enum String változata
        GET: /product/date/{date} - adott dátumon módosított product-okat listázza
                                    date: YYYY-MM-DD formátumban
        GET: /product/id/{id} - adott ID-vel rendelkező Product-al tér vissza
        PUT: /product/id/{id} - adott ID-ű Product adatait változtatja
                                Requestbody: ProductDTO
        DELETE: /product/id/{id} - adott ID-ű Product-ot törli
    
    WAREHOUSE:
        POST: /warehouse - új Warehouse hozzáadás
                           Requestbody: WarehouseDTOWithoutId
        GET: /warehouse - összes Warehouse listázása
        GET: /warehouse/id/{id} - adott ID-ű Warehouse-t adja vissza
        GET: /warehouse/name/{name} - adott nevű Warehouse-t adja vissza
        GET: /warehouse/address/{address} - adott címen lévő Warehouse-t adja vissza
                                    address: 1111 Település név Utca név 22 formátum
        GET: /wrehouse/warehouse/{id} - adott ID-ű Warehouse Product-jait listázza
        GET: /warehouse/workers_needed - összes Warehouse-t listázza hiányzó munkások
                                        alapján sorba rendezve
        PUT: /warehouse/id/{id} - adott ID-ű Warehouse adatait változtatja
                                  Requestbody: WarehouseDTOWithoutId
        Delete: /warehouse/id/{id} - adott ID- ű Warehouse-t törli

    WORKER:
        POST: /worker - új Worker hozzáadás
                        Requestbody: WorkerDTO
        GET: /worker - összes Worker listázása
        GET: /worker/id/{id} - adott ID-ű Worker-t adja vissza
        GET: /worker/position/{position} - adott Position-ben dolgozó Worker-ek
                                           listázása
                                        position: WorkPosition enum String változata
        GET: /worker/salary/{id}/month/{month} - adott ID-ű Worker jelenlegi év
                                                 adott hónapban keresett pénze 
                                                 Double-ként
                                                 month: MM formátumban
        GET: /worker/salary/{id}/start/{start}/end/{end} - adott ID-ű Worker
                                                           két dátum közötti keresete
                                                           Double-ként
                                                    start/end: YYYY-MM-DD formátumban
        POST: /worker/work/id/{id}/warehouse/{warehouse_id}/hours/{hours} - 
                                                    adott ID-ű Worker-hez
                                                    az adott ID-ű Warehouse-ban
                                                    adott órányi munka hozzáadása
                                                    aktuális napra
        GET: /worker/work/{id} - adott ID-ű Worker összes munkanapjának listázáta
        PUT: /worker/id/{id} - adott ID-ű Worker adatainak változtatása
                               Requestbody: WorkerDTO
        DELETE: /worker/id/{id} - adott ID-ű Worker törlése

ADATBÁZISOK (Docker problémák eseten IntellIj-ből való indításhoz léterhozandó):

        Production adatbázis: 
        SPRING_DATASOURCE_URL: jdbc:postgresql://localhost:6666/warestoredb
        SPRING_DATASOURCE_USERNAME: warestoreuser
        SPRING_DATASOURCE_PASSWORD: warepass

        Test adatbázis: 
        SPRING_DATASOURCE_URL: jdbc:postgresql://localhost:6666/warestoredb_test
        SPRING_DATASOURCE_USERNAME: warestoreuser
        SPRING_DATASOURCE_PASSWORD: warepass

    Megfelelő adatbázist a flyway.conf fájl végén cserélni kell!