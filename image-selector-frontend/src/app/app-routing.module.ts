import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GaleriaComponent } from './components/galeria/galeria.component';
import { ImagemExpandidaComponent } from './components/imagem-expandida/imagem-expandida.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';



const routes: Routes = [
  { path: '', redirectTo: 'galeria', pathMatch: 'full' },
  { path: 'galeria', component: GaleriaComponent },
  { path: 'buscar/:tag', component: GaleriaComponent },
  { path: 'image/:id', component: ImagemExpandidaComponent },
  { path: '**', redirectTo: 'pagina-nao-encontrada' },
  { path: 'pagina-nao-encontrada', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
