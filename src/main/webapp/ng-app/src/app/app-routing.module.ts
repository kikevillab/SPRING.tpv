import { WelcomeComponent } from './welcome/welcome.component';

export const AppRoutes = [
    { 
        name: "Welcome",
        description: "Welcome to TPV Online",
        path: 'welcome', 
        showInNav: true,
        component: WelcomeComponent 
    },
    {
      path: '',
      redirectTo: 'welcome',
      pathMatch: 'full'
    }
]