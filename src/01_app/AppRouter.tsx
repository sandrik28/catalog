import { ReactElement } from "react"
import { useSelector } from "react-redux";
import { createBrowserRouter, Navigate } from "react-router-dom";
import { baseLayout } from "./layouts/baseLayouts";
import { NotFoundPage } from "@/02_pages/notFound";
import { MainPage } from "@/02_pages/main";
import { ProfilePage } from "@/02_pages/profile/ui/ProfilePage/Page";
import { ProductPage } from "@/02_pages/product/ui/Page/Page";
import { ProductFormPage } from "@/02_pages/newProduct";
import { LoginPage } from "@/02_pages/login";

type AuthGuardProps = {
  children: ReactElement
}

function AuthGuard({ children }: AuthGuardProps) {
  const isAuthorized = useSelector((state: RootState) => state.session.userId)

  if (!isAuthorized)
    return <Navigate to="/login" />

  return children
}

export function appRouter() {
  return createBrowserRouter([
    {
      element: baseLayout,
      errorElement: <NotFoundPage/>,
      children: [
        {
          path: '/',
          element: (
            <AuthGuard>
              <MainPage />
            </AuthGuard>
          ),
        },
        {
          path: '/profile/:id',
          element: (
            <AuthGuard>
              <ProfilePage />
            </AuthGuard>
          ),
        },
        {
          path: '/product/:id',
          element: (
            <AuthGuard>
              <ProductPage />
            </AuthGuard>
          ),
        },
        {
          path: '/product/:id/edit',
          element: (
            <AuthGuard>
              <ProductPage />
            </AuthGuard>
          ),
        },
        {
          path: 'addNewProduct',
          element: (
            <AuthGuard>
              <ProductFormPage />
            </AuthGuard>
          ),
        },
        {
          path: '/login',
          element: <LoginPage />,
        },
      ],
    },
  ])
}