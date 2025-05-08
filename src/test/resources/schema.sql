-- Tabla: vehicles
CREATE TABLE IF NOT EXISTS vehicles (
    id SERIAL PRIMARY KEY,
    brand TEXT NOT NULL,
    model TEXT NOT NULL,
    year INT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- Tabla: charging_providers
CREATE TABLE IF NOT EXISTS charging_providers (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    logo_url TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- Tabla: profiles
CREATE TABLE IF NOT EXISTS profiles (
    id UUID PRIMARY KEY,
    alias TEXT,
    first_name TEXT,
    last_name TEXT,
    vehicle INT REFERENCES vehicles(id),
    address TEXT,
    preferred_charger_type TEXT,
    subscriptions TEXT[],
    avatar_url TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),
    user_id UUID -- FK a auth.users.id (Supabase, futura integración)
);

-- Si tienes la tabla auth.users en Supabase y quieres la FK, descomenta la siguiente línea:
-- ALTER TABLE profiles ADD CONSTRAINT fk_profiles_user FOREIGN KEY (user_id) REFERENCES auth.users(id); 