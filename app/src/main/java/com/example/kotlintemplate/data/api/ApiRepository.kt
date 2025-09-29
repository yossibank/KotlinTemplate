package com.example.kotlintemplate.data.api

import com.example.kotlintemplate.data.entity.RakutenEntity
import com.example.kotlintemplate.data.entity.RakutenItem
import retrofit2.Retrofit

interface ApiRepositoryInterface {
    suspend fun rakutenSearch(
        keyword: String,
        page: Int
    ): RakutenEntity
}

class ApiRepository(
    client: ApiClientInterface<Retrofit>
) : ApiRepositoryInterface {
    private val apiService: ApiService

    init {
        client.getClient().create(ApiService::class.java).let {
            apiService = it
        }
    }

    override suspend fun rakutenSearch(
        keyword: String,
        page: Int
    ): RakutenEntity {
        return apiService.rakutenSearch(
            keyword = keyword,
            page = page,
            hits = 30,
            formatVersion = 2
        )
    }
}

class FakeApiRepository : ApiRepositoryInterface {
    override suspend fun rakutenSearch(
        keyword: String,
        page: Int
    ): RakutenEntity {
        return RakutenEntity(
            items = arrayListOf(
                RakutenItem(
                    itemName = "ロゴクルーネックTシャツ TOMMY HILFIGER トミーヒルフィガー トップス カットソー・Tシャツ ブラック ホワイト ネイビー【送料無料】[Rakuten Fashion]",
                    itemCode = "tommyhilfiger:10005972",
                    itemPrice = 6600,
                    itemCaption = "【予約商品について】 ※「先行予約販売中」「予約販売中」をご注文の際は予約商品についてをご確認ください。■重要なお知らせ※ 当店では、ギフト配送サービス及びラッピングサービスを行っておりません。ラッピング・ギフト配送について同時に複数の商品を注文された場合メーカー希望小売価格についてご利用ガイドTOMMY HILFIGER（トミーヒルフィガー）ロゴクルーネックTシャツロゴクルーネックTシャツネックの開きやシルエットバランスにこだわった、フェミニンに着こなせる半袖クルーネックTシャツです。前身頃のシグネチャーロゴが、スタイリングをエレガントに仕上げます。今季はパンツやスカートにインして、すっきり着こなすのがおすすめ。ジャケットやパーカとのコーディネートも楽しめて、オールシーズン活躍します。【TOMMY HILFIGER】永遠のアメリカンクラシックをベースにモダンなひねりを加えたデザインが人気のトミーヒルフィガー。都会的で洗練されたメンズ・ウィメンズに加え、チルドレン、ゴルフライン、そしてよりカジュアルなデニムラインと豊富なカテゴリーが揃うグローバルブランド。モデル身長：163cm 着用サイズ:M型番：WW24967-100-XS CT9547【採寸】LARGE/着丈64cm/身幅52.5cm/肩幅41cm/袖丈18cmMEDIUM/着丈63cm/身幅49.5cm/肩幅39cm/袖丈18cmSMALL/着丈60.5cm/身幅48.5cm/肩幅38cm/袖丈17cmX-SMALL/着丈59cm/身幅46cm/肩幅36cm/袖丈16.5cm商品のサイズについて【商品詳細】トルコ製素材：綿100%サイズ：X-SMALL、SMALL、MEDIUM、LARGE※画面上と実物では多少色具合が異なって見える場合もございます。ご了承ください。商品のカラーについて",
                    catchCopy = "TOMMY HILFIGER レディース トップス トミーヒルフィガー",
                    itemUrl = "https://item.rakuten.co.jp/tommyhilfiger/ct9547/?rafcid=wsc_i_is_8a2a6cdb7ded801eeedc109f86ede416",
                    smallImageUrls = arrayListOf(
                        "https://thumbnail.image.rakuten.co.jp/@0_mall/tommyhilfiger/cabinet/item/547/ct9547-01_1.jpg?_ex=64x64",
                        "https://thumbnail.image.rakuten.co.jp/@0_mall/tommyhilfiger/cabinet/item/547/ct9547-02_1.jpg?_ex=64x64",
                        "https://thumbnail.image.rakuten.co.jp/@0_mall/tommyhilfiger/cabinet/item/547/ct9547-09_1.jpg?_ex=64x64"
                    ),
                    mediumImageUrls = arrayListOf(
                        "https://thumbnail.image.rakuten.co.jp/@0_mall/tommyhilfiger/cabinet/item/547/ct9547-01_1.jpg?_ex=128x128",
                        "https://thumbnail.image.rakuten.co.jp/@0_mall/tommyhilfiger/cabinet/item/547/ct9547-02_1.jpg?_ex=128x128",
                        "https://thumbnail.image.rakuten.co.jp/@0_mall/tommyhilfiger/cabinet/item/547/ct9547-09_1.jpg?_ex=128x128"
                    )
                )
            ),
            count = 57071,
            page = 1,
            first = 1,
            last = 30,
            hits = 30,
            carrier = 0,
            pageCount = 100
        )
    }
}