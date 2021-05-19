<h1 align="center">Rebase Cordova Plugin Notification</h1>
<p align="center">Este plugin do cordova para Ionic foi criado especialmente para suprir a necessidade de exibir notificações através do Ionic, uma vez que o único plugin disponível para cordova (cordova-plugin-local-notification) não funciona corretamente em muitos casos de uso.</p>

<p align="center">
    <img src="https://badgen.net/npm/v/@rebase-team/notification"/> 
    <img src="https://badgen.net/npm/dt/@rebase-team/notification"/>
    <img src="https://badgen.net/npm/license/@rebase-team/notification"/>
    <img src="https://badgen.net/npm/types/@rebase-team/notification"/>
    <img src="https://badgen.net/badge/author/MurylloEx/red?icon=label"/>
</p>

Para utilizá-lo você precisa de um projeto Ionic e utilizar o comando descrito na seção abaixo.

## Instalação

```sh
ionic cordova plugin add cordova-plugin-rebase-notification
npm install @rebase-team/notification
```

## Exemplos de uso

``> APPMODULE.TS``
```typescript
import { AppComponent } from "./app.component";
import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { IonicModule } from "@ionic/angular";
import { NotificationService } from "@rebase-team/notification";

@NgModule({
  declarations: [],
  imports: [
    BrowserModule,
    IonicModule.forRoot()
  ],
  providers: [
    NotificationService
  ],
  bootstrap: [AppComponent],
  exports: []
})
export class AppModule { }

```

``> HOME.PAGE.TS``
```typescript
import { NotificationService } from "@rebase-team/notification";

@Component({
  selector: "app-home",
  templateUrl: "home.page.html",
  styleUrls: ["home.page.scss"],
})
export class HomePage implements OnInit {

  constructor(
    private notification: NotificationService
  ) {
    //Cria uma notificação instantaneamente.
    this.notification.show(123, "channelId", "Title", "Text body", "icon/favicon.png");
  }

}
```

## Metadados

Muryllo Pimenta de Oliveira – muryllo.pimenta@upe.br

Distribuído sobre a licença MIT. Veja ``LICENSE`` para mais informações.

## Contribuição

1. Fork it (<https://github.com/MurylloEx/cordova-plugin-rebase-notification/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request

